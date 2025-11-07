package org.seniorcare.residentmanagement.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.util.UUID;

public class FamilyLink {

    private final UUID id;
    private final UUID familyMemberId;
    private String relationship;
    private boolean isPrimaryContact;
    private final Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    public FamilyLink(UUID id, UUID familyMemberId, String relationship, Boolean isPrimaryContact,
                      Instant createdAt, Instant updatedAt, Instant deletedAt) {

        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        if (familyMemberId == null)
            throw new IllegalArgumentException("FamilyMemberID cannot be null when reconstituting.");

        this.id = id;
        this.familyMemberId = familyMemberId;
        this.relationship = relationship;
        this.isPrimaryContact = (isPrimaryContact != null) ? isPrimaryContact : false; // Garante que não seja nulo
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static FamilyLink create(UUID familyMemberId, String relationship, boolean isPrimaryContact) {
        if (familyMemberId == null) {
            throw new BadRequestException("Family Member ID is required.");
        }
        if (relationship == null || relationship.isBlank()) {
            throw new BadRequestException("Relationship is required.");
        }

        Instant now = Instant.now();
        return new FamilyLink(UUID.randomUUID(), familyMemberId, relationship, isPrimaryContact, now, now, null);
    }


    public UUID getId() {
        return id;
    }

    public UUID getFamilyMemberId() {
        return familyMemberId;
    }

    public String getRelationship() {
        return relationship;
    }

    public boolean isPrimaryContact() {
        return isPrimaryContact;
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

    public void makePrimaryContact() {
        if (!this.isPrimaryContact) {
            this.isPrimaryContact = true;
            this.updatedAt = Instant.now();
        }
    }

    public void removePrimaryContact() {
        if (this.isPrimaryContact) {
            this.isPrimaryContact = false;
            this.updatedAt = Instant.now();
        }
    }

    protected void updateRelationship(String newRelationship) {
        if (newRelationship == null || newRelationship.isBlank()) {
            throw new BadRequestException("Relationship cannot be empty.");
        }
        if (!this.relationship.equals(newRelationship)) {
            this.relationship = newRelationship;
            this.updatedAt = Instant.now();
        }
    }
}