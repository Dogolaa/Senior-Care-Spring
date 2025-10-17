package org.seniorcare.identityaccess.api.rest.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToManagerRequest(
        UUID userId,
        LocalDate admissionDate,
        String department,
        String shift
) {
}
