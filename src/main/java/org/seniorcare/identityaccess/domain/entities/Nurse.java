package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public class Nurse extends Employee {
    private String coren;
    private String specialization;
    private String shift;

    private Nurse(User user, LocalDate admissionDate, String coren, String specialization, String shift) {
        super(user, admissionDate);
        this.coren = coren;
        this.specialization = specialization;
        this.shift = shift;
    }

    public Nurse(UUID id, User user, LocalDate admissionDate, String coren, String specialization, String shift, Instant deletedAt) {
        super(id, user, admissionDate, deletedAt);
        this.coren = coren;
        this.specialization = specialization;
        this.shift = shift;
    }

    public String getCoren() {
        return coren;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getShift() {
        return shift;
    }

    public static Nurse create(User user, LocalDate admissionDate, String coren, String specialization, String shift) {
        if (coren == null || coren.isBlank()) throw new BadRequestException("COREN is required for a Nurse.");

        return new Nurse(user, admissionDate, coren, specialization, shift);
    }

    public void updateDetails(String newCoren, String newSpecialization, String newShift) {
        if (newCoren == null || newCoren.isBlank()) {
            throw new BadRequestException("COREN cannot be empty during update.");
        }
        if (newSpecialization == null || newSpecialization.isBlank()) {
            throw new BadRequestException("Specialization cannot be empty during update.");
        }
        if (newShift == null || newShift.isBlank()) {
            throw new BadRequestException("Shift cannot be empty during update.");
        }

        this.coren = newCoren;
        this.specialization = newSpecialization;
        this.shift = newShift;

    }
}