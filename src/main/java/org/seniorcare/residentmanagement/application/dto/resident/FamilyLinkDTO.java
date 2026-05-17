package org.seniorcare.residentmanagement.application.dto.resident;

import java.time.Instant;
import java.util.UUID;

public class FamilyLinkDTO {

    private final UUID id;
    private final UUID familyMemberId;
    private final String relationship;
    private final boolean isPrimaryContact;
    private final Instant createdAt;
    private final Instant updatedAt;

    public FamilyLinkDTO(UUID id, UUID familyMemberId, String relationship, boolean isPrimaryContact,
                         Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.familyMemberId = familyMemberId;
        this.relationship = relationship;
        this.isPrimaryContact = isPrimaryContact;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
}
