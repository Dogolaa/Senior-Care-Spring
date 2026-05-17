package org.seniorcare.health.api.rest.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.seniorcare.health.api.rest.dto.CreateHealthRecordRequest;
import org.seniorcare.health.api.rest.dto.UpdateHealthRecordRequest;
import org.seniorcare.health.application.commands.impl.CreateHealthRecordCommand;
import org.seniorcare.health.application.commands.impl.UpdateHealthRecordCommand;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface HealthRecordDtoMapper {

    CreateHealthRecordCommand toCommand(CreateHealthRecordRequest request);

    @Mapping(target = "healthRecordId", source = "healthRecordId")
    UpdateHealthRecordCommand toCommand(UpdateHealthRecordRequest request, UUID healthRecordId);
}
