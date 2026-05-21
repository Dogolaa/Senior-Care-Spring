package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddPhotoToMedicationRecordCommand;
import org.seniorcare.health.domain.repositories.IMedicationRecordRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddPhotoToMedicationRecordCommandHandler {

    private final IMedicationRecordRepository medicationRecordRepository;

    public AddPhotoToMedicationRecordCommandHandler(IMedicationRecordRepository medicationRecordRepository) {
        this.medicationRecordRepository = medicationRecordRepository;
    }

    @Transactional
    public void handle(AddPhotoToMedicationRecordCommand command) {
        medicationRecordRepository.addPhoto(command.medicationRecordId(), command.photoUrl());
    }
}
