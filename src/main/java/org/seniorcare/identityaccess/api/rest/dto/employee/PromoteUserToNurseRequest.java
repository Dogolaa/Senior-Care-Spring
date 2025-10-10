package org.seniorcare.identityaccess.api.rest.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToNurseRequest(
        UUID userId,
        LocalDate admissionDate,
        String coren,
        String specialization,
        String shift
) {
}
