package org.seniorcare.identityaccess.application.commands.impl.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToDoctorCommand(
        UUID userId,
        LocalDate admissionDate,
        String crm,
        String specialization,
        String shift
) {
}
