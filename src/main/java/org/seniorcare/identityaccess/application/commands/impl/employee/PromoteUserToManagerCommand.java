package org.seniorcare.identityaccess.application.commands.impl.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToManagerCommand(
        UUID userId,
        LocalDate admissionDate,
        String department,
        String shift
) {
}
