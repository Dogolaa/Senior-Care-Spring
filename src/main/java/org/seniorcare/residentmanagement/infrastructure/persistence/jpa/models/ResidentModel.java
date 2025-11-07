package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.seniorcare.shared.infrastructure.persistence.Auditable;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "residents")
@SQLRestriction("deleted_at IS NULL")
public class ResidentModel extends Auditable {

    @Id
    private UUID id;

    @Column(name = "cpf", nullable = false, unique = true)
    private String cpf;

    @Column(name = "rg", nullable = false)
    private String rg;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(
            mappedBy = "resident",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<FamilyLinkModel> familyLinks = new ArrayList<>();

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "resident_allergies",
            joinColumns = @JoinColumn(name = "resident_id")
    )
    @Column(name = "allergy_description")
    private List<String> allergies = new ArrayList<>();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gender", nullable = false)
    private String gender;

    @Column(name = "blood_type", nullable = false)
    private String bloodType;

    @Column(name = "is_active", nullable = false)
    private boolean isActive;

    @Column(name = "admission_date", nullable = false)
    private LocalDate admissionDate;

    @Column(name = "room", nullable = false)
    private String room;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public ResidentModel() {
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<FamilyLinkModel> getFamilyLinks() {
        return familyLinks;
    }

    public void setFamilyLinks(List<FamilyLinkModel> familyLinks) {
        this.familyLinks = familyLinks;
    }

    public List<String> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<String> allergies) {
        this.allergies = allergies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public void setAdmissionDate(LocalDate admissionDate) {
        this.admissionDate = admissionDate;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResidentModel that)) return false;
        if (!super.equals(o)) return false;
        return isActive == that.isActive &&
                Objects.equals(id, that.id) &&
                Objects.equals(cpf, that.cpf) &&
                Objects.equals(rg, that.rg) &&
                Objects.equals(dateOfBirth, that.dateOfBirth) &&
                Objects.equals(name, that.name) &&
                Objects.equals(gender, that.gender) &&
                Objects.equals(bloodType, that.bloodType) &&
                Objects.equals(admissionDate, that.admissionDate) &&
                Objects.equals(room, that.room) &&
                Objects.equals(deletedAt, that.deletedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, cpf, rg, dateOfBirth, name, gender,
                bloodType, isActive, admissionDate, room, deletedAt);
    }
}