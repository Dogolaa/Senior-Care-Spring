package org.seniorcare.health.application.queries.handlers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.health.application.queries.dto.ActivityRecordResponse;
import org.seniorcare.health.application.queries.impl.FindActivityRecordByResidentIdQuery;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FindActivityRecordByResidentIdQueryHandlerTest {

    @Mock
    private IActivityRecordRepository activityRecordRepository;

    @InjectMocks
    private FindActivityRecordByResidentIdQueryHandler handler;

    private UUID residentId;
    private UUID staffId;

    @BeforeEach
    void setUp() {
        residentId = UUID.randomUUID();
        staffId = UUID.randomUUID();
    }

    @Test
    void handle_withExistingRecord_shouldReturnResponse() {
        ActivityRecord record = ActivityRecord.create(residentId, staffId);
        LocalDateTime start = LocalDateTime.now();
        record.logActivity("Tomar Sol", "Jardim", start, start.plusHours(1), staffId, null);

        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(record));

        ActivityRecordResponse response = handler.handle(new FindActivityRecordByResidentIdQuery(residentId));

        assertNotNull(response);
        assertEquals(residentId, response.residentId());
        assertEquals(1, response.history().size());
        assertEquals("Tomar Sol", response.history().get(0).activityName());
    }

    @Test
    void handle_whenRecordNotFound_shouldThrowResourceNotFound() {
        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> handler.handle(new FindActivityRecordByResidentIdQuery(residentId)));
    }

    @Test
    void handle_withEmptyHistory_shouldReturnEmptyHistoryList() {
        ActivityRecord record = ActivityRecord.create(residentId, staffId);

        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(record));

        ActivityRecordResponse response = handler.handle(new FindActivityRecordByResidentIdQuery(residentId));

        assertTrue(response.history().isEmpty());
    }

    @Test
    void handle_shouldMapPhotoUrlsFromHistory() {
        ActivityRecord record = ActivityRecord.create(residentId, staffId);
        LocalDateTime start = LocalDateTime.now();
        record.logActivity("Fisioterapia", null, start, null, staffId, null);

        when(activityRecordRepository.findByResidentId(residentId)).thenReturn(Optional.of(record));

        ActivityRecordResponse response = handler.handle(new FindActivityRecordByResidentIdQuery(residentId));

        assertNotNull(response.history().get(0).photoUrls());
        assertTrue(response.history().get(0).photoUrls().isEmpty());
    }
}
