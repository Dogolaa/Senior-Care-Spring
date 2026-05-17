package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.seniorcare.health.domain.entities.Medication;
import org.seniorcare.health.infrastructure.persistence.jpa.models.MedicationModel;

@Mapper(componentModel = "spring")
public interface MedicationPersistenceMapper {
    MedicationModel toModel(Medication domain);
    Medication toDomain(MedicationModel model);
}
