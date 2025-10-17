package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Manager extends Employee {
    private String department;
    private String shift;

    private Manager(User user, LocalDate admissionDate, String department, String shift) {
        super(user, admissionDate);
        this.department = department;
        this.shift = shift;
    }

    public Manager(UUID id, User user, LocalDate admissionDate, String department, String shift, Instant deletedAt) {
        super(id, user, admissionDate, deletedAt);
        this.department = department;
        this.shift = shift;
    }

    public String getDepartment() {
        return department;
    }

    public String getShift() {
        return shift;
    }

    public static Manager create(User user, LocalDate admissionDate, String department, String shift) {
        return new Manager(user, admissionDate, department, shift);
    }

    public void updateDetails(String newDepartment, String newShift) {
        if (newDepartment == null || newDepartment.isBlank()) {
            throw new BadRequestException("Department cannot be empty during update.");
        }
        if (newShift == null || newShift.isBlank()) {
            throw new BadRequestException("Shift cannot be empty during update.");
        }

        this.department = newDepartment;
        this.shift = newShift;

    }
}