package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seniorcare.health.domain.entities.MedicationRecord;
import org.seniorcare.health.infrastructure.persistence.jpa.models.MedicationRecordModel;

@Mapper(componentModel = "spring")
public interface MedicationRecordPersistenceMapper {

    @Mapping(target = "photos", ignore = true)
    MedicationRecordModel toModel(MedicationRecord domain);

    @Mapping(target = "photoUrls", expression = "java(model.getPhotos().stream().map(p -> p.getPhotoUrl()).collect(java.util.stream.Collectors.toList()))")
    MedicationRecord toDomain(MedicationRecordModel model);
}
