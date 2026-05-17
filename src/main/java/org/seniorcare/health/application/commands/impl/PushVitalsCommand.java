package org.seniorcare.health.application.commands.impl;

import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.util.UUID;

public record PushVitalsCommand(
        UUID residentId,
        UUID recordedById,
        Integer heartRate,
        Float saturation,
        String bloodPressure,
        Float temperature,
        VitalSignsSource source
) {
}
