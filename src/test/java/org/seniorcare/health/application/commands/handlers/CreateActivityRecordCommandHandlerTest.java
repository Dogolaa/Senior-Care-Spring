package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.CreateActivityRecordCommand;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateActivityRecordCommandHandlerTest {

    @Mock
    private IActivityRecordRepository activityRecordRepository;

    @InjectMocks
    private CreateActivityRecordCommandHandler handler;

    private UUID residentId;
    private UUID staffId;

    @BeforeEach
    void setUp() {
        residentId = UUID.randomUUID();
        staffId = UUID.randomUUID();
    }

    @Test
    void handle_withValidCommand_shouldSaveAndReturnId() {
        CreateActivityRecordCommand command = new CreateActivityRecordCommand(residentId, staffId);

        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.empty());
        when(activityRecordRepository.save(any(ActivityRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID result = handler.handle(command);

        assertNotNull(result);
        verify(activityRecordRepository).save(any(ActivityRecord.class));
    }

    @Test
    void handle_whenRecordAlreadyExists_shouldThrowBadRequest() {
        CreateActivityRecordCommand command = new CreateActivityRecordCommand(residentId, staffId);

        ActivityRecord existing = ActivityRecord.create(residentId, staffId);
        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(existing));

        assertThrows(BadRequestException.class, () -> handler.handle(command));
        verify(activityRecordRepository, never()).save(any());
    }
}
