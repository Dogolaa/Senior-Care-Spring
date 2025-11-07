package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.seniorcare.shared.infrastructure.persistence.Auditable;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "resident_family_links")
public class FamilyLinkModel extends Auditable {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resident_id", nullable = false)
    private ResidentModel resident;

    @Column(name = "user_id", nullable = false)
    private UUID familyMemberId;

    @Column(name = "relationship", nullable = false)
    private String relationship;

    @Column(name = "is_primary_contact", nullable = false)
    private boolean isPrimaryContact;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public FamilyLinkModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ResidentModel getResident() {
        return resident;
    }

    public void setResident(ResidentModel resident) {
        this.resident = resident;
    }

    public UUID getFamilyMemberId() {
        return familyMemberId;
    }

    public void setFamilyMemberId(UUID familyMemberId) {
        this.familyMemberId = familyMemberId;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
    }

    public void setPrimaryContact(boolean primaryContact) {
        isPrimaryContact = primaryContact;
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
        if (!(o instanceof FamilyLinkModel that)) return false;
        if (!super.equals(o)) return false;
        return isPrimaryContact == that.isPrimaryContact &&
                Objects.equals(id, that.id) &&
                Objects.equals(familyMemberId, that.familyMemberId) &&
                Objects.equals(relationship, that.relationship);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id, familyMemberId, relationship, isPrimaryContact);
    }
}