package org.seniorcare.identityaccess.application.dto.employee;

import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDTO(
        UUID employeeId,
        UUID userId,
        String employeeType,
        LocalDate admissionDate,
        String specialization,
        String shift,
        String crm,
        String coren
) {
}
