package org.seniorcare.residentmanagement.domain.aggregates;

import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.domain.events.ResidentAdmittedEvent;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.domain.vo.Gender;
import org.seniorcare.residentmanagement.domain.vo.Rg;
import org.seniorcare.shared.domain.BaseAggregateRoot;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.*;

public class Resident extends BaseAggregateRoot {

    private final UUID id;
    private final Cpf cpf;
    private final Rg rg;
    private final LocalDate dateOfBirth;

    private final List<FamilyLink> familyLinks = new ArrayList<>();
    private final List<String> allergies = new ArrayList<>();
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
            String name, Cpf cpf, Rg rg, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, List<String> initialAllergies, String room) {

        if (name == null || name.trim().isEmpty()) throw new BadRequestException("Resident name cannot be empty.");
        if (dateOfBirth == null) throw new BadRequestException("Date of birth cannot be null.");
        if (room == null || room.trim().isEmpty()) throw new BadRequestException("Room number cannot be empty.");

        this.id = UUID.randomUUID();
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        if (initialAllergies != null) {
            initialAllergies.forEach(this::addAllergyInternal);
        }
        this.isActive = true;
        this.admissionDate = LocalDate.now();
        this.room = room;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.deletedAt = null;
    }

    public Resident(
            UUID id, String name, Cpf cpf, Rg rg, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, List<String> allergies, Boolean isActive,
            LocalDate admissionDate, String room, List<FamilyLink> familyLinks,
            Instant createdAt, Instant updatedAt, Instant deletedAt) {

        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        if (cpf == null) throw new IllegalArgumentException("CPF cannot be null when reconstituting.");

        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.rg = rg;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        if (allergies != null) {
            this.allergies.addAll(allergies);
        }
        this.isActive = (isActive != null) ? isActive : true;
        this.admissionDate = admissionDate;
        this.room = room;
        if (familyLinks != null) {
            this.familyLinks.addAll(familyLinks);
        }
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Resident admit(
            String name, String cpfValue, String rgValue, LocalDate dateOfBirth,
            Gender gender, BloodType bloodType, List<String> initialAllergies, String room) {

        Cpf validCpf = new Cpf(cpfValue);
        Rg validRg = (rgValue != null && !rgValue.isBlank()) ? new Rg(rgValue) : null;

        Resident newResident = new Resident(
                name, validCpf, validRg, dateOfBirth,
                gender, bloodType, initialAllergies, room);

        newResident.registerEvent(new ResidentAdmittedEvent(newResident.getId(), newResident.getName()));

        return newResident;
    }

    public UUID getId() {
        return id;
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

    public List<FamilyLink> getFamilyLinks() {
        return Collections.unmodifiableList(familyLinks);
    }

    public List<String> getAllergies() {
        return Collections.unmodifiableList(allergies);
    }

    private void addAllergyInternal(String allergyDescription) {
        if (allergyDescription == null || allergyDescription.isBlank()) {
            throw new BadRequestException("Allergy description cannot be empty.");
        }
        String normalizedAllergy = allergyDescription.trim().toUpperCase();
        if (!this.allergies.contains(normalizedAllergy)) {
            this.allergies.add(normalizedAllergy);
        }
    }

    public void addAllergy(String allergyDescription) {
        this.addAllergyInternal(allergyDescription);
        this.updatedAt = Instant.now();
    }

    public void removeAllergy(String allergyDescription) {
        if (allergyDescription == null || allergyDescription.isBlank()) return;
        String normalizedAllergy = allergyDescription.trim().toUpperCase();
        boolean removed = this.allergies.remove(normalizedAllergy);
        if (removed) {
            this.updatedAt = Instant.now();
        }
    }

    public void addFamilyLink(UUID familyMemberId, String relationship, boolean isPrimaryContact) {
        if (this.familyLinks.stream().anyMatch(v -> v.getFamilyMemberId().equals(familyMemberId))) {
            throw new IllegalStateException("This family member already has a link to the resident.");
        }

        if (isPrimaryContact) {
            this.familyLinks.forEach(FamilyLink::removePrimaryContact);
        }

        FamilyLink newLink = FamilyLink.create(familyMemberId, relationship, isPrimaryContact);
        this.familyLinks.add(newLink);
        this.updatedAt = Instant.now();
    }

    public void removeFamilyLink(UUID familyLinkId) {
        Optional<FamilyLink> linkOpt = this.familyLinks.stream().filter(v -> v.getId().equals(familyLinkId)).findFirst();
        if (linkOpt.isPresent()) {
            if (linkOpt.get().isPrimaryContact() && this.familyLinks.size() == 1) {
                throw new IllegalStateException("Cannot remove the last and only primary contact.");
            }
            boolean removed = this.familyLinks.removeIf(v -> v.getId().equals(familyLinkId));
            if (removed) {
                this.updatedAt = Instant.now();
            }
        }
    }

    public void setPrimaryContact(UUID familyLinkId) {
        Optional<FamilyLink> linkOpt = this.familyLinks.stream().filter(v -> v.getId().equals(familyLinkId)).findFirst();
        if (linkOpt.isEmpty()) {
            throw new BadRequestException("Family link not found for this resident.");
        }
        if (!linkOpt.get().isPrimaryContact()) {
            this.familyLinks.forEach(FamilyLink::removePrimaryContact);
            linkOpt.get().makePrimaryContact();
            this.updatedAt = Instant.now();
        }
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
}