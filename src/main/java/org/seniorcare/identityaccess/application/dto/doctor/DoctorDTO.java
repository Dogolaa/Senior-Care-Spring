package org.seniorcare.identityaccess.application.dto.doctor;

import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

public class DoctorDTO extends RepresentationModel<DoctorDTO> {

    private final UUID id;
    private final UUID employeeId;
    private final String name;
    private final String email;
    private final String phone;
    private final String crm;
    private final String specialization;
    private final String shift;
    private final boolean isActive;
    private final Instant createdAt;
    private final Instant updatedAt;

    public DoctorDTO(UUID id, UUID employeeId, String name, String email, String phone,
                     String crm, String specialization, String shift, boolean isActive, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.employeeId = employeeId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.crm = crm;
        this.specialization = specialization;
        this.shift = shift;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public UUID getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
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

    public boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}