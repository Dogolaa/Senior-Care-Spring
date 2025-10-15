package org.seniorcare.identityaccess.api.rest.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public record PromoteUserToDoctorRequest(
        UUID userId,
        LocalDate admissionDate,
        String crm,
        String specialization,
        String shift
) {
}
