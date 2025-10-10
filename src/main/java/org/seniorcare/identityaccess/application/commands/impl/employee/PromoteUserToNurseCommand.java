package org.seniorcare.identityaccess.application.commands.impl.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToNurseCommand(
        UUID userId,
        LocalDate admissionDate,
        String coren,
        String specialization,
        String shift
) {
}
