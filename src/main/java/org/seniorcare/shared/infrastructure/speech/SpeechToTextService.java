package org.seniorcare.shared.infrastructure.speech;

import com.microsoft.cognitiveservices.speech.*;
import com.microsoft.cognitiveservices.speech.audio.AudioConfig;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import java.util.concurrent.Future;

@Service
public class SpeechToTextService {

    private static final Logger logger = LoggerFactory.getLogger(SpeechToTextService.class);

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

        Path tempFilePath = null;
        try (SpeechConfig speechConfig = SpeechConfig.fromSubscription(subscriptionKey, region)) {
            speechConfig.setSpeechRecognitionLanguage("pt-BR");

            tempFilePath = Files.createTempFile(UUID.randomUUID().toString(), ".wav");
            Files.copy(audioFile.getInputStream(), tempFilePath, StandardCopyOption.REPLACE_EXISTING);

            try (AudioConfig audioConfig = AudioConfig.fromWavFileInput(tempFilePath.toString());
                 SpeechRecognizer speechRecognizer = new SpeechRecognizer(speechConfig, audioConfig)) {

                logger.info("Enviando áudio para reconhecimento a partir do arquivo temporário: {}", tempFilePath);
                Future<SpeechRecognitionResult> task = speechRecognizer.recognizeOnceAsync();
                SpeechRecognitionResult result = task.get();

                if (result.getReason() == ResultReason.RecognizedSpeech) {
                    logger.info("Fala reconhecida com sucesso: '{}'", result.getText());
                    return result.getText();
                } else if (result.getReason() == ResultReason.NoMatch) {
                    logger.warn("Não foi possível reconhecer a fala (NoMatch). Detalhes: {}", result.toString());
                    throw new BadRequestException("Não foi possível reconhecer a fala. O áudio pode estar vazio ou conter apenas ruído.");
                } else if (result.getReason() == ResultReason.Canceled) {
                    CancellationDetails cancellation = CancellationDetails.fromResult(result);
                    logger.error("Reconhecimento do Azure cancelado. Motivo: {}", cancellation.getReason());
                    if (cancellation.getReason() == CancellationReason.Error) {
                        logger.error("Código de Erro: {}", cancellation.getErrorCode());
                        logger.error("Detalhes do Erro: {}", cancellation.getErrorDetails());
                    }
                    throw new RuntimeException("Reconhecimento de fala cancelado: " + cancellation.getErrorDetails());
                } else {
                    logger.error("Erro inesperado durante o reconhecimento de fala. Razão: {}", result.getReason());
                    throw new RuntimeException("Erro durante o reconhecimento de fala: " + result.getReason());
                }
            }
        } catch (Exception e) {
            logger.error("Falha ao processar o arquivo de áudio. A causa raiz é:", e);
            throw new RuntimeException("Falha ao processar o arquivo de áudio.", e);
        } finally {
            if (tempFilePath != null) {
                try {
                    Files.deleteIfExists(tempFilePath);
                    logger.info("Arquivo temporário {} deletado com sucesso.", tempFilePath);
                } catch (Exception e) {
                    logger.error("Falha ao deletar o arquivo temporário: {}", tempFilePath, e);
                }
            }
        }
    }
}