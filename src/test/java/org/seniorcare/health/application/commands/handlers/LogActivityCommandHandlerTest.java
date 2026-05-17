package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.LogActivityCommand;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LogActivityCommandHandlerTest {

    @Mock
    private IActivityRecordRepository activityRecordRepository;

    @InjectMocks
    private LogActivityCommandHandler handler;

    private UUID activityRecordId;
    private UUID staffId;
    private ActivityRecord record;

    @BeforeEach
    void setUp() {
        activityRecordId = UUID.randomUUID();
        staffId = UUID.randomUUID();
        record = ActivityRecord.create(UUID.randomUUID(), staffId);
    }

    @Test
    void handle_withValidCommand_shouldReturnHistoryId() {
        LocalDateTime start = LocalDateTime.now();
        LogActivityCommand command = new LogActivityCommand(
                activityRecordId, "Clube de Leitura", "Sessão semanal", start, start.plusHours(1), staffId, "Boa participação");

        when(activityRecordRepository.findById(activityRecordId)).thenReturn(Optional.of(record));
        when(activityRecordRepository.save(any(ActivityRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID historyId = handler.handle(command);

        assertNotNull(historyId);
        verify(activityRecordRepository).save(record);
    }

    @Test
    void handle_whenRecordNotFound_shouldThrowResourceNotFound() {
        LogActivityCommand command = new LogActivityCommand(
                activityRecordId, "Tomar Sol", null, LocalDateTime.now(), null, staffId, null);

        when(activityRecordRepository.findById(activityRecordId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> handler.handle(command));
        verify(activityRecordRepository, never()).save(any());
    }

    @Test
    void handle_withBlankActivityName_shouldThrowBadRequest() {
        LogActivityCommand command = new LogActivityCommand(
                activityRecordId, "", null, LocalDateTime.now(), null, staffId, null);

        when(activityRecordRepository.findById(activityRecordId)).thenReturn(Optional.of(record));

        assertThrows(BadRequestException.class, () -> handler.handle(command));
        verify(activityRecordRepository, never()).save(any());
    }

    @Test
    void handle_withEndBeforeStart_shouldThrowBadRequest() {
        LocalDateTime start = LocalDateTime.now();
        LogActivityCommand command = new LogActivityCommand(
                activityRecordId, "Fisioterapia", null, start, start.minusHours(1), staffId, null);

        when(activityRecordRepository.findById(activityRecordId)).thenReturn(Optional.of(record));

        assertThrows(BadRequestException.class, () -> handler.handle(command));
        verify(activityRecordRepository, never()).save(any());
    }
}
