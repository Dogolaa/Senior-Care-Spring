package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddPhotoToHealthRecordHistoryCommand;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.application.storage.IFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddPhotoToHealthRecordHistoryCommandHandler {

    private final IHealthRecordRepository healthRecordRepository;
    private final IFileStorageService fileStorageService;

    public AddPhotoToHealthRecordHistoryCommandHandler(IHealthRecordRepository healthRecordRepository,
                                                        IFileStorageService fileStorageService) {
        this.healthRecordRepository = healthRecordRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void handle(AddPhotoToHealthRecordHistoryCommand command) {
        String photoUrl = fileStorageService.upload(
                command.fileContent(),
                command.filename(),
                command.contentType()
        );
        healthRecordRepository.addPhotoToHistory(command.healthRecordHistoryId(), photoUrl);
    }
}
