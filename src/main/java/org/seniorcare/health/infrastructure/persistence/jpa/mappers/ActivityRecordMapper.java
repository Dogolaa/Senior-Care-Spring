package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordModel;

@Mapper(componentModel = "spring", uses = ActivityRecordHistoryMapper.class)
public interface ActivityRecordMapper {

    ActivityRecordModel toModel(ActivityRecord record);

    ActivityRecord toDomain(ActivityRecordModel model);
}
