package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.AddPhotoToActivityHistoryCommand;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.application.storage.IFileStorageService;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddPhotoToActivityHistoryCommandHandlerTest {

    @Mock
    private IActivityRecordRepository activityRecordRepository;

    @Mock
    private IFileStorageService fileStorageService;

    @InjectMocks
    private AddPhotoToActivityHistoryCommandHandler handler;

    @Test
    void handle_shouldUploadFileAndSaveUrl() {
        UUID historyId = UUID.randomUUID();
        byte[] content = "image content".getBytes();
        String filename = "foto.jpg";
        String contentType = "image/jpeg";
        String uploadedUrl = "https://blob.azure.net/seniorcare-media/foto.jpg";

        AddPhotoToActivityHistoryCommand command = new AddPhotoToActivityHistoryCommand(historyId, content, filename, contentType);

        when(fileStorageService.upload(content, filename, contentType)).thenReturn(uploadedUrl);

        handler.handle(command);

        verify(fileStorageService).upload(content, filename, contentType);
        verify(activityRecordRepository).addPhotoToHistory(historyId, uploadedUrl);
    }
}
