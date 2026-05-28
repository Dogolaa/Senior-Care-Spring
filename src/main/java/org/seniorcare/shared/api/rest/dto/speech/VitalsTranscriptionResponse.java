package org.seniorcare.shared.api.rest.dto.speech;

public record VitalsTranscriptionResponse(
        String rawTranscription,
        String bloodPressure,
        Float weight,
        Float height,
        Float temperature,
        Integer heartRate,
        Float saturation
) {}
