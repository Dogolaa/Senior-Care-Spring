package org.seniorcare.health.application.commands.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.commands.impl.PushVitalsCommand;
import org.seniorcare.health.application.ports.output.IVitalAlertPort;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.health.domain.vo.VitalSignsSource;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PushVitalsCommandHandlerTest {

    @Mock
    private IHealthRecordRepository healthRecordRepository;

    @Mock
    private IVitalAlertPort vitalAlertPort;

    @InjectMocks
    private PushVitalsCommandHandler handler;

    private UUID residentId;
    private UUID staffId;
    private HealthRecord existingRecord;

    @BeforeEach
    void setUp() {
        residentId = UUID.randomUUID();
        staffId = UUID.randomUUID();
        existingRecord = new HealthRecord(residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);
    }

    @Test
    void handle_withValidCommand_shouldUpdateVitalsAndSave() {
        PushVitalsCommand command = new PushVitalsCommand(
                residentId, staffId, 85, 97f, null, null, VitalSignsSource.WEARABLE);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(existingRecord));

        handler.handle(command);

        verify(healthRecordRepository).save(existingRecord);
        assertEquals(85, existingRecord.getHeartRate());
        assertEquals(97f, existingRecord.getSaturation());
        assertEquals("120/80", existingRecord.getBloodPressure());
    }

    @Test
    void handle_whenRecordNotFound_shouldThrowResourceNotFound() {
        PushVitalsCommand command = new PushVitalsCommand(
                residentId, staffId, 85, 97f, null, null, VitalSignsSource.WEARABLE);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> handler.handle(command));
        verify(healthRecordRepository, never()).save(any());
    }

    @Test
    void handle_withAllVitals_shouldUpdateAllFields() {
        PushVitalsCommand command = new PushVitalsCommand(
                residentId, staffId, 90, 96f, "130/85", 37.2f, VitalSignsSource.MEDICAL_DEVICE);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(existingRecord));

        handler.handle(command);

        assertEquals(90, existingRecord.getHeartRate());
        assertEquals(96f, existingRecord.getSaturation());
        assertEquals("130/85", existingRecord.getBloodPressure());
        assertEquals(37.2f, existingRecord.getTemperature());
        verify(healthRecordRepository).save(existingRecord);
    }
}
