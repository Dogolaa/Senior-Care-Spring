package org.seniorcare.shared.infrastructure.speech;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class SpeechToTextService {

    private static final Logger logger = LoggerFactory.getLogger(SpeechToTextService.class);

    private static final int TARGET_SAMPLE_RATE = 16000;
    private static final int TARGET_BITS = 16;
    private static final int TARGET_CHANNELS = 1;

    @Value("${azure.speech.subscription-key}")
    private String subscriptionKey;

    @Value("${azure.speech.region}")
    private String region;

    public String transcribeAudioFile(MultipartFile audioFile) {
        if (audioFile == null || audioFile.isEmpty()) {
            logger.error("Arquivo de áudio recebido é nulo ou vazio.");
            throw new BadRequestException("Arquivo de áudio é obrigatório.");
        }
        logger.info("Processando arquivo de áudio. Nome: [{}], Tamanho: [{} bytes]",
                audioFile.getOriginalFilename(), audioFile.getSize());

        Path rawPath = null;
        Path convertedPath = null;
        try (SpeechConfig speechConfig = SpeechConfig.fromSubscription(subscriptionKey, region)) {
            speechConfig.setSpeechRecognitionLanguage("pt-BR");

            rawPath = Files.createTempFile(UUID.randomUUID().toString(), ".wav");
            Files.copy(audioFile.getInputStream(), rawPath, StandardCopyOption.REPLACE_EXISTING);

            convertedPath = convertToAzureFormat(rawPath);
            logger.info("Áudio convertido para 16kHz mono 16-bit. Enviando para Azure: {}", convertedPath);

            try (AudioConfig audioConfig = AudioConfig.fromWavFileInput(convertedPath.toString());
                 SpeechRecognizer recognizer = new SpeechRecognizer(speechConfig, audioConfig)) {

                StringBuilder transcription = new StringBuilder();
                CountDownLatch latch = new CountDownLatch(1);
                final String[] cancelError = {null};

                recognizer.recognized.addEventListener((s, e) -> {
                    if (e.getResult().getReason() == ResultReason.RecognizedSpeech) {
                        String text = e.getResult().getText();
                        logger.info("Segmento reconhecido: '{}'", text);
                        transcription.append(text).append(" ");
                    }
                });

                recognizer.sessionStopped.addEventListener((s, e) -> latch.countDown());

                recognizer.canceled.addEventListener((s, e) -> {
                    CancellationDetails details = CancellationDetails.fromResult(e.getResult());
                    if (details.getReason() == CancellationReason.Error) {
                        logger.error("Reconhecimento cancelado com erro. Código: {}, Detalhes: {}",
                                details.getErrorCode(), details.getErrorDetails());
                        cancelError[0] = details.getErrorDetails();
                    }
                    latch.countDown();
                });

                recognizer.startContinuousRecognitionAsync().get();
                boolean finished = latch.await(60, TimeUnit.SECONDS);
                recognizer.stopContinuousRecognitionAsync().get();

                if (!finished) {
                    logger.warn("Timeout aguardando reconhecimento contínuo.");
                }
                if (cancelError[0] != null) {
                    throw new RuntimeException("Reconhecimento de fala cancelado: " + cancelError[0]);
                }

                String result = transcription.toString().trim();
                if (result.isEmpty()) {
                    logger.warn("Reconhecimento concluído mas nenhuma fala detectada no áudio.");
                    throw new BadRequestException("Não foi possível reconhecer a fala. Verifique se o áudio contém fala clara.");
                }

                logger.info("Transcrição completa: '{}'", result);
                return result;
            }
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Falha ao processar o arquivo de áudio.", e);
            throw new RuntimeException("Falha ao processar o arquivo de áudio.", e);
        } finally {
            deleteSilently(rawPath);
            deleteSilently(convertedPath);
        }
    }

    // Converte qualquer WAV PCM para 16kHz mono 16-bit little-endian, que é o formato exigido pelo Azure Speech SDK.
    private Path convertToAzureFormat(Path inputPath) throws Exception {
        try (AudioInputStream sourceStream = AudioSystem.getAudioInputStream(inputPath.toFile())) {
            AudioFormat sourceFormat = sourceStream.getFormat();
            float sourceRate = sourceFormat.getSampleRate();
            int sourceChannels = sourceFormat.getChannels();
            int sourceBits = sourceFormat.getSampleSizeInBits();
            boolean bigEndian = sourceFormat.isBigEndian();

            logger.info("Formato original do áudio: {}Hz, {} canais, {}-bit", sourceRate, sourceChannels, sourceBits);

            byte[] sourceBytes = sourceStream.readAllBytes();
            int bytesPerSample = sourceBits / 8;
            int frameSize = bytesPerSample * sourceChannels;
            int totalFrames = sourceBytes.length / frameSize;

            // Decodifica para float[] mono mesclando canais
            float[] monoSamples = new float[totalFrames];
            for (int i = 0; i < totalFrames; i++) {
                float sum = 0;
                for (int ch = 0; ch < sourceChannels; ch++) {
                    int offset = i * frameSize + ch * bytesPerSample;
                    sum += readSampleAsFloat(sourceBytes, offset, bytesPerSample, bigEndian);
                }
                monoSamples[i] = sum / sourceChannels;
            }

            // Reamostrar para TARGET_SAMPLE_RATE usando interpolação linear
            float ratio = sourceRate / TARGET_SAMPLE_RATE;
            int targetFrames = (int) (totalFrames / ratio);
            byte[] pcmOut = new byte[targetFrames * 2];

            for (int i = 0; i < targetFrames; i++) {
                float srcIdx = i * ratio;
                int floor = (int) srcIdx;
                float frac = srcIdx - floor;
                int ceil = Math.min(floor + 1, totalFrames - 1);
                float interpolated = monoSamples[floor] * (1 - frac) + monoSamples[ceil] * frac;
                short sample = (short) Math.max(-32768, Math.min(32767, interpolated));
                // little-endian 16-bit
                pcmOut[i * 2] = (byte) (sample & 0xFF);
                pcmOut[i * 2 + 1] = (byte) ((sample >> 8) & 0xFF);
            }

            AudioFormat targetFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    TARGET_SAMPLE_RATE, TARGET_BITS, TARGET_CHANNELS, 2, TARGET_SAMPLE_RATE, false
            );

            Path outputPath = Files.createTempFile(UUID.randomUUID().toString(), "-azure.wav");
            try (AudioInputStream targetStream = new AudioInputStream(
                    new ByteArrayInputStream(pcmOut), targetFormat, targetFrames)) {
                AudioSystem.write(targetStream, AudioFileFormat.Type.WAVE, outputPath.toFile());
            }
            return outputPath;
        }
    }

    private float readSampleAsFloat(byte[] data, int offset, int bytesPerSample, boolean bigEndian) {
        if (bytesPerSample == 2) {
            short s = bigEndian
                    ? (short) ((data[offset] << 8) | (data[offset + 1] & 0xFF))
                    : (short) ((data[offset + 1] << 8) | (data[offset] & 0xFF));
            return s;
        }
        // 8-bit PCM é unsigned
        return (data[offset] & 0xFF) - 128.0f;
    }

    private void deleteSilently(Path path) {
        if (path == null) return;
        try {
            Files.deleteIfExists(path);
        } catch (Exception e) {
            logger.error("Falha ao deletar arquivo temporário: {}", path, e);
        }
    }
}
