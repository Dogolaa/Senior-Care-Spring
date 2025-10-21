package org.seniorcare.identityaccess.application.dto.employee;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public record EmployeeDetailsDTO(
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
        String specialization,
        String shift
) {
    public EmployeeDetailsDTO(
            UUID userId, UUID employeeId, String name, String email, String phone,
            LocalDate admissionDate, boolean isActive, Instant createdAt, String role,
            String coren, String crm, String department,
            String nurseSpec, String doctorSpec, String managerShift, String nurseShift, String doctorShift) {

        this(userId, employeeId, name, email, phone, admissionDate, isActive, createdAt, role,
                coren, crm, department,
                (role.equals("NURSE") ? nurseSpec : (role.equals("DOCTOR") ? doctorSpec : null)),
                (role.equals("NURSE") ? nurseShift : (role.equals("DOCTOR") ? doctorShift : (role.equals("MANAGER") ? managerShift : null)))
        );
    }
}