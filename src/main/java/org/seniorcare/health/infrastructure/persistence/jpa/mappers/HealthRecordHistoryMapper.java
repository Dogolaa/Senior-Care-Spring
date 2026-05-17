package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seniorcare.health.domain.entities.HealthRecordHistory;
import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordHistoryModel;

@Mapper(componentModel = "spring")
public interface HealthRecordHistoryMapper {

    @Mapping(target = "healthRecord", ignore = true)
    @Mapping(target = "photos", ignore = true)
    HealthRecordHistoryModel toModel(HealthRecordHistory healthRecordHistory);

    @Mapping(source = "healthRecord.id", target = "healthRecordId")
    @Mapping(target = "photoUrls", expression = "java(model.getPhotos().stream().map(p -> p.getPhotoUrl()).collect(java.util.stream.Collectors.toList()))")
    HealthRecordHistory toDomain(HealthRecordHistoryModel model);
}
