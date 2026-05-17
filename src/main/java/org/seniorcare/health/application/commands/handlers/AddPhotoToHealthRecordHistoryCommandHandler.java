package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddPhotoToHealthRecordHistoryCommand;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddPhotoToHealthRecordHistoryCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public AddPhotoToHealthRecordHistoryCommandHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    @Transactional
    public void handle(AddPhotoToHealthRecordHistoryCommand command) {
        healthRecordRepository.addPhotoToHistory(command.healthRecordHistoryId(), command.photoUrl());
    }
}
