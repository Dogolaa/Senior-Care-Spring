package org.seniorcare.identityaccess.application.commands.impl.employee;

import java.time.LocalDate;
import java.util.UUID;

public record UpdateEmployeeCommand(
        UUID employeeId,
        LocalDate admissionDate,
        String specialization,
        String shift,
        String crm,
        String coren
) {
}
