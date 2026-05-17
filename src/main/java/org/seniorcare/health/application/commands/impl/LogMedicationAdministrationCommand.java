package org.seniorcare.health.application.commands.impl;

import java.time.LocalDate;
import java.util.UUID;

public record LogMedicationAdministrationCommand(
        UUID residentId,
        UUID medicationId,
        LocalDate administrationDate,
        UUID administeredById,
        String dose
) {
}
