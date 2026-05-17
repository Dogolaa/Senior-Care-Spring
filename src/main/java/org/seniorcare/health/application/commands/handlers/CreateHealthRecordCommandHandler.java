package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.CreateHealthRecordCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateHealthRecordCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public CreateHealthRecordCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    public UUID handle(CreateHealthRecordCommand command) {
        healthRecordRepository.findByResidentId(command.residentId()).ifPresent(r -> {
            throw new BadRequestException("Health record for resident " + command.residentId() + " already exists.");
        });

        HealthRecord healthRecord = new HealthRecord(
                command.residentId(),
                command.updatedById(),
                command.height(),
                command.weight(),
                command.bloodPressure(),
                command.heartRate(),
                command.temperature(),
                command.saturation());

        HealthRecord savedRecord = healthRecordRepository.save(healthRecord);
        return savedRecord.getId();
    }
}
