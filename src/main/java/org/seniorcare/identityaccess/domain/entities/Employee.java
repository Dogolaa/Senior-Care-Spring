package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;

public abstract class Employee {
    protected final UUID id;
    protected final User user;
    protected LocalDate admissionDate;
    protected Instant deletedAt;

    protected Employee(User user, LocalDate admissionDate) {
        if (user == null) throw new BadRequestException("User cannot be null for an Employee");
        if (admissionDate == null) throw new BadRequestException("Admission date cannot be null");

        this.id = UUID.randomUUID();
        this.user = user;
        this.admissionDate = admissionDate;
        this.deletedAt = null;
    }

    protected Employee(UUID id, User user, LocalDate admissionDate, Instant deletedAt) {
        this.id = id;
        this.user = user;
        this.admissionDate = admissionDate;
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void softDelete() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("Employee is already deleted.");
        }
        this.deletedAt = Instant.now();
    }

    public void reactivate() {
        if (this.deletedAt == null) {
            throw new IllegalStateException("Employee is already active.");
        }
        this.deletedAt = null;
    }

    public void changeAdmissionDate(LocalDate newAdmissionDate) {
        if (newAdmissionDate == null) {
            throw new BadRequestException("Admission date cannot be null.");
        }
        this.admissionDate = newAdmissionDate;
    }

}