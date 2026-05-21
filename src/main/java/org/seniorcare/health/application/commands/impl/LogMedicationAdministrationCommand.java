package org.seniorcare.health.application.commands.impl;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogMedicationAdministrationCommand(
        UUID residentId,
        UUID medicationId,
        LocalDateTime administrationDate,
        UUID administeredById,
        String dose
) {
}
