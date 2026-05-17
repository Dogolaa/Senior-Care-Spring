package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.seniorcare.health.domain.entities.MedicationRecord;
import org.seniorcare.health.infrastructure.persistence.jpa.models.MedicationRecordModel;

@Mapper(componentModel = "spring")
public interface MedicationRecordPersistenceMapper {
    MedicationRecordModel toModel(MedicationRecord domain);
    MedicationRecord toDomain(MedicationRecordModel model);
}
