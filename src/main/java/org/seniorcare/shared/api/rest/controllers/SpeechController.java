package org.seniorcare.shared.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.shared.infrastructure.speech.SpeechToTextService;
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

    public SpeechController(SpeechToTextService speechToTextService) {
        this.speechToTextService = speechToTextService;
    }

    @Operation(summary = "Transcreve um arquivo de áudio para texto")
    @PostMapping(value = "/transcribe", consumes = "multipart/form-data")
    public ResponseEntity<String> transcribeAudio(@RequestParam("audioFile") MultipartFile audioFile) {
        String transcribedText = speechToTextService.transcribeAudioFile(audioFile);
        return ResponseEntity.ok(transcribedText);
    }
}