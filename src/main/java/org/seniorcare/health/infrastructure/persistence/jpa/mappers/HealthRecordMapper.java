package org.seniorcare.health.infrastructure.persistence.jpa.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordModel;

@Mapper(componentModel = "spring", uses = HealthRecordHistoryMapper.class)
public interface HealthRecordMapper {

    HealthRecordModel toModel(HealthRecord healthRecord);

    HealthRecord toDomain(HealthRecordModel healthRecordModel);
}
