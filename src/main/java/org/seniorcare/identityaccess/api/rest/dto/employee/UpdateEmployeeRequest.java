package org.seniorcare.identityaccess.api.rest.dto.employee;

import java.time.LocalDate;

public record UpdateEmployeeRequest(
        LocalDate admissionDate,
        String specialization,
        String shift,
        String crm,
        String coren
) {
}