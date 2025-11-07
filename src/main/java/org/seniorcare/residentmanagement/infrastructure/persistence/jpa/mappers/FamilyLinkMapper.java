package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.mappers;

import org.seniorcare.residentmanagement.domain.entities.FamilyLink;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.FamilyLinkModel;
import org.springframework.stereotype.Component;

@Component
public class FamilyLinkMapper {

    public FamilyLinkMapper() {
    }

    public FamilyLinkModel toModel(FamilyLink entity) {
        if (entity == null) return null;

        FamilyLinkModel model = new FamilyLinkModel();

        model.setId(entity.getId());
        model.setFamilyMemberId(entity.getFamilyMemberId());
        model.setRelationship(entity.getRelationship());
        model.setPrimaryContact(entity.isPrimaryContact());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public FamilyLink toEntity(FamilyLinkModel model) {
        if (model == null) return null;

        return new FamilyLink(
                model.getId(),
                model.getFamilyMemberId(),
                model.getRelationship(),
                model.isPrimaryContact(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}