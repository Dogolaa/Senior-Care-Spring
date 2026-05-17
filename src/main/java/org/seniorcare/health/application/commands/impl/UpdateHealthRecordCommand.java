package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record UpdateHealthRecordCommand(
    UUID healthRecordId,
    UUID updatedById,
    Float height,
    Float weight,
    String bloodPressure,
    Integer heartRate,
    Float temperature,
    Float saturation
) {
}
