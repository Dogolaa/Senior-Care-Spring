package org.seniorcare.identityaccess.infrastructure.persistence.jpa.projections;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDetailsProjection(
        UUID userId,
        UUID employeeId,
        String name,
        String email,
        String phone,
        LocalDate admissionDate,
        boolean isActive,
        Instant createdAt,
        String role,
        String coren,
        String crm,
        String department,
        String nurseSpecialization,
        String doctorSpecialization,
        String managerShift,
        String nurseShift,
        String doctorShift
) {
}
