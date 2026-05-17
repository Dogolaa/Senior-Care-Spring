package org.seniorcare.health.application.queries.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.queries.dto.HealthRecordResponse;
import org.seniorcare.health.application.queries.impl.FindHealthRecordByResidentIdQuery;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindHealthRecordByResidentIdQueryHandlerTest {

    @Mock
    private IHealthRecordRepository healthRecordRepository;

    @InjectMocks
    private FindHealthRecordByResidentIdQueryHandler handler;

    private UUID residentId;
    private UUID staffId;

    @BeforeEach
    void setUp() {
        residentId = UUID.randomUUID();
        staffId = UUID.randomUUID();
    }

    @Test
    void handle_withExistingRecord_shouldReturnResponse() {
        HealthRecord record = new HealthRecord(residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(record));

        HealthRecordResponse response = handler.handle(new FindHealthRecordByResidentIdQuery(residentId));

        assertNotNull(response);
        assertEquals(residentId, response.residentId());
        assertEquals(1.75f, response.height());
        assertEquals(70f, response.weight());
        assertEquals("120/80", response.bloodPressure());
        assertEquals(1, response.history().size());
    }

    @Test
    void handle_whenRecordNotFound_shouldThrowResourceNotFound() {
        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> handler.handle(new FindHealthRecordByResidentIdQuery(residentId)));
    }

    @Test
    void handle_shouldMapHistoryWithSourceAndPhotoUrls() {
        HealthRecord record = new HealthRecord(residentId, staffId, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        when(healthRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(record));

        HealthRecordResponse response = handler.handle(new FindHealthRecordByResidentIdQuery(residentId));

        assertNotNull(response.history().get(0).source());
        assertNotNull(response.history().get(0).photoUrls());
        assertTrue(response.history().get(0).photoUrls().isEmpty());
    }
}
