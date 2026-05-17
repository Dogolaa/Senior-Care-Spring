package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seniorcare.health.domain.entities.ActivityRecordHistory;
import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordHistoryModel;

@Mapper(componentModel = "spring")
public interface ActivityRecordHistoryMapper {

    @Mapping(target = "activityRecord", ignore = true)
    @Mapping(target = "photos", ignore = true)
    ActivityRecordHistoryModel toModel(ActivityRecordHistory history);

    @Mapping(source = "activityRecord.id", target = "activityRecordId")
    @Mapping(target = "photoUrls", expression = "java(model.getPhotos().stream().map(p -> p.getPhotoUrl()).collect(java.util.stream.Collectors.toList()))")
    ActivityRecordHistory toDomain(ActivityRecordHistoryModel model);
}
