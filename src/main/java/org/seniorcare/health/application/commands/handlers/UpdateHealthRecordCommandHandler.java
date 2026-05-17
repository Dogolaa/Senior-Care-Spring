package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.UpdateHealthRecordCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdateHealthRecordCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public UpdateHealthRecordCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    public UUID handle(UpdateHealthRecordCommand command) {
        HealthRecord healthRecord = healthRecordRepository.findById(command.healthRecordId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record with id " + command.healthRecordId() + " not found."));

        healthRecord.update(
                command.updatedById(),
                command.height(),
                command.weight(),
                command.bloodPressure(),
                command.heartRate(),
                command.temperature(),
                command.saturation());

        HealthRecord updatedRecord = healthRecordRepository.save(healthRecord);
        return updatedRecord.getId();
    }
}
