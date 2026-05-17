package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record CreateHealthRecordCommand(
    UUID residentId,
    UUID updatedById,
    Float height,
    Float weight,
    String bloodPressure,
    Integer heartRate,
    Float temperature,
    Float saturation
) {
}
