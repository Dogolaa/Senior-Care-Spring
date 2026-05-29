package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.RemoveConditionCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RemoveConditionCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public RemoveConditionCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Transactional
    public void handle(RemoveConditionCommand command) {
        HealthRecord record = healthRecordRepository.findByResidentId(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record for resident " + command.residentId() + " not found."));

        record.removeCondition(command.conditionDescription());

        healthRecordRepository.save(record);
    }
}
