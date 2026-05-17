package org.seniorcare.residentmanagement.application.dto.resident;

import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class ResidentDTO extends RepresentationModel<ResidentDTO> {

    private final UUID id;
    private final String name;
    private final String cpf;
    private final String rg;
    private final LocalDate dateOfBirth;
    private final String gender;
    private final String bloodType;
    private final boolean isActive;
    private final LocalDate admissionDate;
    private final String room;
    private final List<String> allergies;
    private final List<FamilyLinkDTO> familyLinks;
    private final Instant createdAt;
    private final Instant updatedAt;

    public ResidentDTO(UUID id, String name, String cpf, String rg, LocalDate dateOfBirth,
                       String gender, String bloodType, boolean isActive, LocalDate admissionDate,
                       String room, List<String> allergies, List<FamilyLinkDTO> familyLinks,
                       Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.isActive = isActive;
        this.admissionDate = admissionDate;
        this.room = room;
        this.allergies = allergies;
        this.familyLinks = familyLinks;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCpf() {
        return cpf;
    }

    public String getRg() {
        return rg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public boolean isActive() {
        return isActive;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public String getRoom() {
        return room;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public List<FamilyLinkDTO> getFamilyLinks() {
        return familyLinks;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
