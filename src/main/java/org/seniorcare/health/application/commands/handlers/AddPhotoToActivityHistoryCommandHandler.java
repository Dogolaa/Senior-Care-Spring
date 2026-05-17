package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddPhotoToActivityHistoryCommand;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddPhotoToActivityHistoryCommandHandler {

    private final IActivityRecordRepository activityRecordRepository;

    public AddPhotoToActivityHistoryCommandHandler(IActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    @Transactional
    public void handle(AddPhotoToActivityHistoryCommand command) {
        activityRecordRepository.addPhotoToHistory(command.activityRecordHistoryId(), command.photoUrl());
    }
}
