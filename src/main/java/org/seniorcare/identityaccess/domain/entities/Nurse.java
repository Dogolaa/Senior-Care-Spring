package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

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

    public static Nurse create(User user, LocalDate admissionDate, String coren, String specialization, String shift) {
        if (coren == null || coren.isBlank()) throw new BadRequestException("COREN is required for a Nurse.");

        return new Nurse(user, admissionDate, coren, specialization, shift);
    }

    public Nurse(UUID id, User user, LocalDate admissionDate, String coren, String specialization, String shift) {
        super(id, user, admissionDate);
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
}