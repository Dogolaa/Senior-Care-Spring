package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.seniorcare.health.domain.entities.Prescription;
import org.seniorcare.health.infrastructure.persistence.jpa.models.PrescriptionModel;

@Mapper(componentModel = "spring")
public interface PrescriptionPersistenceMapper {
    PrescriptionModel toModel(Prescription domain);
    Prescription toDomain(PrescriptionModel model);
}
