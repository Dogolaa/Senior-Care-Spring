package org.seniorcare.health.application.commands.impl;

import java.time.LocalDate;
import java.util.UUID;

public record CreatePrescriptionCommand(
        UUID medicalRecordId,
        UUID medicationId,
        String dosage,
        LocalDate startDate,
        LocalDate endDate
) {
}
