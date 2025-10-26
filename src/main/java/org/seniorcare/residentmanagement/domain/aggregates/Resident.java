package org.seniorcare.residentmanagement.domain.aggregates;

import org.seniorcare.residentmanagement.domain.events.ResidentAdmittedEvent;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.domain.vo.Gender;
import org.seniorcare.residentmanagement.domain.vo.Rg;
import org.seniorcare.shared.domain.BaseAggregateRoot;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.UUID;


public class Resident extends BaseAggregateRoot {

    private final UUID id;
    private final Cpf cpf;
    private final Rg rg;
    private final LocalDate dateOfBirth;

    private UUID responsibleId;
    private String name;
    private Gender gender;
    private BloodType bloodType;
    private Boolean isActive;
    private LocalDate admissionDate;
    private String room;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Resident(
            UUID responsibleId, String name, Cpf cpf, Rg rg, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, String room) {

        if (responsibleId == null) throw new BadRequestException("Responsible ID cannot be null for admission.");
        if (name == null || name.trim().isEmpty()) throw new BadRequestException("Resident name cannot be empty.");
        if (dateOfBirth == null) throw new BadRequestException("Date of birth cannot be null.");
        if (room == null || room.trim().isEmpty()) throw new BadRequestException("Room number cannot be empty.");

        this.id = UUID.randomUUID();
        this.responsibleId = responsibleId;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        //TODO: allergy
        this.isActive = true;
        this.admissionDate = LocalDate.now();
        this.room = room;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.deletedAt = null;
    }

    public Resident(
            UUID id, UUID responsibleId, String name, Cpf cpf, Rg rg, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, Boolean isActive,
            LocalDate admissionDate, String room, Instant createdAt, Instant updatedAt,
            Instant deletedAt) {

        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        if (cpf == null) throw new IllegalArgumentException("CPF cannot be null when reconstituting.");
        if (responsibleId == null)
            throw new IllegalArgumentException("Responsible ID cannot be null when reconstituting.");


        this.id = id;
        this.responsibleId = responsibleId;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        // Sem allergy
        this.isActive = (isActive != null) ? isActive : true;
        this.admissionDate = admissionDate;
        this.room = room;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Resident admit(
            UUID responsibleId, String name, String cpfValue, String rgValue, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, String room) {

        Cpf validCpf = new Cpf(cpfValue);
        Rg validRg = (rgValue != null && !rgValue.isBlank()) ? new Rg(rgValue) : null;

        Resident newResident = new Resident(
                responsibleId, name, validCpf, validRg, dateOfBirth,
                gender, bloodType, room);


        newResident.registerEvent(new ResidentAdmittedEvent(newResident.getId(), newResident.getName()));

        return newResident;
    }

    public UUID getId() {
        return id;
    }

    public UUID getResponsibleId() {
        return responsibleId;
    }

    public String getName() {
        return name;
    }

    public Cpf getCpf() {
        return cpf;
    }

    public Rg getRg() {
        return rg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Gender getGender() {
        return gender;
    }

    public BloodType getBloodType() {
        return bloodType;
    }

    // Sem getAllergy()
    public Boolean isActive() {
        return isActive;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public String getRoom() {
        return room;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }


    public void transferRoom(String newRoom) {
        if (!this.isActive) {
            throw new IllegalStateException("Cannot transfer an inactive resident.");
        }
        if (newRoom == null || newRoom.trim().isEmpty()) {
            throw new BadRequestException("New room number cannot be empty.");
        }
        this.room = newRoom;
        this.updatedAt = Instant.now();
    }

    public void recordDischarge() {
        if (!this.isActive) {
            throw new IllegalStateException("Resident is already inactive.");
        }
        this.isActive = false;
        this.deletedAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public void changePrimaryResponsible(UUID newResponsibleId) {
        if (newResponsibleId == null) {
            throw new BadRequestException("New responsible ID cannot be null.");
        }
        if (!this.isActive) {
            throw new IllegalStateException("Cannot change responsible for an inactive resident.");
        }
        this.responsibleId = newResponsibleId;
        this.updatedAt = Instant.now();
    }

}