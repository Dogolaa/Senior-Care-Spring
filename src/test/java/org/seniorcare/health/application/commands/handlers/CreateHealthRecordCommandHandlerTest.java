package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.CreateHealthRecordCommand;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateHealthRecordCommandHandlerTest {

    @Mock
    private IHealthRecordRepository healthRecordRepository;

    @InjectMocks
    private CreateHealthRecordCommandHandler handler;

    private UUID residentId;
    private UUID staffId;

    @BeforeEach
    void setUp() {
        residentId = UUID.randomUUID();
        staffId = UUID.randomUUID();
    }

    @Test
    void handle_withValidCommand_shouldSaveAndReturnId() {
        CreateHealthRecordCommand command = new CreateHealthRecordCommand(
                residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.empty());
        when(healthRecordRepository.save(any(HealthRecord.class))).thenAnswer(inv -> inv.getArgument(0));

        UUID result = handler.handle(command);

        assertNotNull(result);
        verify(healthRecordRepository).save(any(HealthRecord.class));
    }

    @Test
    void handle_whenRecordAlreadyExists_shouldThrowBadRequest() {
        CreateHealthRecordCommand command = new CreateHealthRecordCommand(
                residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        HealthRecord existing = new HealthRecord(residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);
        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(existing));

        assertThrows(BadRequestException.class, () -> handler.handle(command));
        verify(healthRecordRepository, never()).save(any());
    }
}
