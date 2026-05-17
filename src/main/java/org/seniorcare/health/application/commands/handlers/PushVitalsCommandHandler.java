package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.PushVitalsCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PushVitalsCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public PushVitalsCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Transactional
    public void handle(PushVitalsCommand command) {
        HealthRecord record = healthRecordRepository.findByResidentId(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record for resident " + command.residentId() + " not found."));

        record.recordVitals(
                command.recordedById(),
                command.heartRate(),
                command.saturation(),
                command.bloodPressure(),
                command.temperature(),
                command.source()
        );

        healthRecordRepository.save(record);
    }
}
