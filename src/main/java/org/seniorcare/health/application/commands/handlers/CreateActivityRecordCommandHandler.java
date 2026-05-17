package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.CreateActivityRecordCommand;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreateActivityRecordCommandHandler {

    private final IActivityRecordRepository activityRecordRepository;

    public CreateActivityRecordCommandHandler(IActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    public UUID handle(CreateActivityRecordCommand command) {
        activityRecordRepository.findByResidentId(command.residentId()).ifPresent(r -> {
            throw new BadRequestException(
                    "Activity record for resident " + command.residentId() + " already exists.");
        });

        ActivityRecord record = ActivityRecord.create(command.residentId(), command.conductedById());
        ActivityRecord saved = activityRecordRepository.save(record);
        return saved.getId();
    }
}
