package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Doctor extends Employee {

    private String crm;
    private String specialization;
    private String shift;

    private Doctor(User user, LocalDate admissionDate, String crm, String specialization, String shift) {
        super(user, admissionDate);
        this.crm = crm;
        this.specialization = specialization;
        this.shift = shift;
    }

    public Doctor(UUID id, User user, LocalDate admissionDate, String crm, String specialization, String shift, Instant deletedAt) {
        super(id, user, admissionDate, deletedAt);
        this.crm = crm;
        this.specialization = specialization;
        this.shift = shift;
    }

    public String getCrm() {
        return crm;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getShift() {
        return shift;
    }

    public static Doctor create(User user, LocalDate admissionDate, String crm, String specialization, String shift) {
        if (crm == null || crm.isBlank()) throw new BadRequestException("CRM is required for a Doctor.");

        return new Doctor(user, admissionDate, crm, specialization, shift);
    }

    public void updateDetails(String newCrm, String newSpecialization, String newShift) {
        if (newCrm == null || newCrm.isBlank()) {
            throw new BadRequestException("CRM cannot be empty during update.");
        }
        if (newSpecialization == null || newSpecialization.isBlank()) {
            throw new BadRequestException("Specialization cannot be empty during update.");
        }
        if (newShift == null || newShift.isBlank()) {
            throw new BadRequestException("Shift cannot be empty during update.");
        }

        this.crm = newCrm;
        this.specialization = newSpecialization;
        this.shift = newShift;

    }

}
