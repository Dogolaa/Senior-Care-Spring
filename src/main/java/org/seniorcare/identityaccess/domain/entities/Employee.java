package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Employee {
    protected final UUID id;
    protected final User user;
    protected LocalDate admissionDate;

    protected Employee(User user, LocalDate admissionDate) {
        if (user == null) throw new BadRequestException("User cannot be null for an Employee");
        if (admissionDate == null) throw new BadRequestException("Admission date cannot be null");

        this.id = UUID.randomUUID();
        this.user = user;
        this.admissionDate = admissionDate;
    }

    protected Employee(UUID id, User user, LocalDate admissionDate) {
        this.id = id;
        this.user = user;
        this.admissionDate = admissionDate;
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
}