package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.LogActivityCommand;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class LogActivityCommandHandler {

    private final IActivityRecordRepository activityRecordRepository;

    public LogActivityCommandHandler(IActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    @Transactional
    public UUID handle(LogActivityCommand command) {
        ActivityRecord record = activityRecordRepository.findById(command.activityRecordId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity record not found: " + command.activityRecordId()));

        UUID historyId = record.logActivity(
                command.activityName(),
                command.description(),
                command.startDateTime(),
                command.endDateTime(),
                command.conductedById(),
                command.notes()
        );

        activityRecordRepository.save(record);
        return historyId;
    }
}
