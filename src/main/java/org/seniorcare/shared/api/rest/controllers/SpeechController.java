package org.seniorcare.shared.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.shared.api.rest.dto.speech.VitalsTranscriptionResponse;
import org.seniorcare.shared.infrastructure.speech.SpeechToTextService;
import org.seniorcare.shared.infrastructure.speech.VitalsTextParser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/speech")
@Tag(name = "Speech Services", description = "Endpoints para serviços de fala")
public class SpeechController {

    private final SpeechToTextService speechToTextService;
    private final VitalsTextParser vitalsTextParser;

    public SpeechController(SpeechToTextService speechToTextService, VitalsTextParser vitalsTextParser) {
        this.speechToTextService = speechToTextService;
        this.vitalsTextParser = vitalsTextParser;
    }

    @Operation(summary = "Transcreve um arquivo de áudio para texto bruto")
    @PostMapping(value = "/transcribe", consumes = "multipart/form-data")
    public ResponseEntity<String> transcribeAudio(
            @Parameter(description = "Arquivo de áudio WAV")
            @RequestParam("audioFile") MultipartFile audioFile) {
        String transcribedText = speechToTextService.transcribeAudioFile(audioFile);
        return ResponseEntity.ok(transcribedText);
    }

    @Operation(
            summary = "Transcreve áudio de sinais vitais e retorna campos estruturados",
            description = "Aceita um áudio WAV onde o profissional de saúde fala os sinais vitais " +
                    "(ex: 'pressão 120 por 80, temperatura 36 vírgula 5, saturação 98') " +
                    "e retorna um objeto com cada campo já extraído e pronto para preencher o formulário."
    )
    @PostMapping(value = "/transcribe-vitals", consumes = "multipart/form-data")
    public ResponseEntity<VitalsTranscriptionResponse> transcribeVitals(
            @Parameter(description = "Arquivo de áudio WAV com os sinais vitais ditados")
            @RequestParam("audioFile") MultipartFile audioFile) {
        String transcribedText = speechToTextService.transcribeAudioFile(audioFile);
        VitalsTranscriptionResponse vitals = vitalsTextParser.parse(transcribedText);
        return ResponseEntity.ok(vitals);
    }
}
