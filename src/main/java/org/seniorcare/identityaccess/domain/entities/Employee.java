package org.seniorcare.identityaccess.domain.entities;

import java.time.LocalDate;
import java.util.UUID;

public abstract class Employee {

    protected UUID id;
    protected User user;
    protected LocalDate AdmissionDate;

    protected Employee(UUID id, User user, LocalDate admissionDate) {
        this.id = id;
        this.user = user;
        AdmissionDate = admissionDate;
    }

    public UUID getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getAdmissionDate() {
        return AdmissionDate;
    }
}
