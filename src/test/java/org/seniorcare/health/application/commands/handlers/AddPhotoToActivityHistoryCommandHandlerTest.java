package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.AddPhotoToActivityHistoryCommand;
import org.seniorcare.health.domain.entities.ActivityRecordPhoto;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AddPhotoToActivityHistoryCommandHandlerTest {

    @Mock
    private IActivityRecordRepository activityRecordRepository;

    @InjectMocks
    private AddPhotoToActivityHistoryCommandHandler handler;

    @Test
    void handle_shouldSavePhotoUrl() {
        UUID historyId = UUID.randomUUID();
        String photoUrl = "https://cdn.example.com/photos/foto.jpg";

        AddPhotoToActivityHistoryCommand command = new AddPhotoToActivityHistoryCommand(historyId, photoUrl);

        handler.handle(command);

        verify(activityRecordRepository).addPhotoToHistory(eq(historyId), any(ActivityRecordPhoto.class));
    }
}
