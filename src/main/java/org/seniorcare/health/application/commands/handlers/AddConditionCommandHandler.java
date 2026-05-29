package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddConditionCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddConditionCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public AddConditionCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Transactional
    public void handle(AddConditionCommand command) {
        HealthRecord record = healthRecordRepository.findByResidentId(command.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record for resident " + command.residentId() + " not found."));

        record.addCondition(command.conditionDescription());

        healthRecordRepository.save(record);
    }
}
