package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.AddPhotoToActivityHistoryCommand;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.application.storage.IFileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddPhotoToActivityHistoryCommandHandler {

    private final IActivityRecordRepository activityRecordRepository;
    private final IFileStorageService fileStorageService;

    public AddPhotoToActivityHistoryCommandHandler(IActivityRecordRepository activityRecordRepository,
                                                    IFileStorageService fileStorageService) {
        this.activityRecordRepository = activityRecordRepository;
        this.fileStorageService = fileStorageService;
    }

    @Transactional
    public void handle(AddPhotoToActivityHistoryCommand command) {
        String photoUrl = fileStorageService.upload(
                command.fileContent(),
                command.filename(),
                command.contentType()
        );
        activityRecordRepository.addPhotoToHistory(command.activityRecordHistoryId(), photoUrl);
    }
}
