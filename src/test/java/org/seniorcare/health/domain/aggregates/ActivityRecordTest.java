package org.seniorcare.health.domain.aggregates;

import org.junit.jupiter.api.Test;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class ActivityRecordTest {

    private static final UUID RESIDENT_ID = UUID.randomUUID();
    private static final UUID STAFF_ID = UUID.randomUUID();

    @Test
    void create_withValidData_shouldInitializeRecord() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);

        assertNotNull(record.getId());
        assertEquals(RESIDENT_ID, record.getResidentId());
        assertEquals(STAFF_ID, record.getConductedById());
        assertNull(record.getLastActivityDate());
        assertTrue(record.getHistory().isEmpty());
    }

    @Test
    void create_withNullResidentId_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> ActivityRecord.create(null, STAFF_ID));
    }

    @Test
    void create_withNullConductedById_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> ActivityRecord.create(RESIDENT_ID, null));
    }

    @Test
    void logActivity_withValidData_shouldAddHistoryAndReturnId() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plusHours(1);

        UUID historyId = record.logActivity("Clube de Leitura", "Leitura coletiva", start, end, STAFF_ID, "Ótima participação");

        assertNotNull(historyId);
        assertEquals(1, record.getHistory().size());
        assertEquals("Clube de Leitura", record.getHistory().get(0).getActivityName());
        assertNotNull(record.getLastActivityDate());
    }

    @Test
    void logActivity_withNullActivityName_shouldThrowBadRequest() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);

        assertThrows(BadRequestException.class, () ->
                record.logActivity(null, "desc", LocalDateTime.now(), null, STAFF_ID, null));
    }

    @Test
    void logActivity_withBlankActivityName_shouldThrowBadRequest() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);

        assertThrows(BadRequestException.class, () ->
                record.logActivity("   ", "desc", LocalDateTime.now(), null, STAFF_ID, null));
    }

    @Test
    void logActivity_withNullStartDateTime_shouldThrowBadRequest() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);

        assertThrows(BadRequestException.class, () ->
                record.logActivity("Tomar Sol", "desc", null, null, STAFF_ID, null));
    }

    @Test
    void logActivity_withEndBeforeStart_shouldThrowBadRequest() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.minusHours(1);

        assertThrows(BadRequestException.class, () ->
                record.logActivity("Tomar Sol", "desc", start, end, STAFF_ID, null));
    }

    @Test
    void logActivity_withNullEndDateTime_shouldSucceed() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);

        assertDoesNotThrow(() ->
                record.logActivity("Tomar Sol", "desc", LocalDateTime.now(), null, STAFF_ID, null));
    }

    @Test
    void logActivity_withOptionalNotes_shouldStoreNotes() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);
        LocalDateTime start = LocalDateTime.now();

        record.logActivity("Fisioterapia", null, start, start.plusMinutes(30), STAFF_ID, "Progresso excelente");

        assertEquals("Progresso excelente", record.getHistory().get(0).getNotes());
        assertNull(record.getHistory().get(0).getDescription());
    }

    @Test
    void logActivity_multipleActivities_shouldAccumulateHistory() {
        ActivityRecord record = ActivityRecord.create(RESIDENT_ID, STAFF_ID);
        LocalDateTime start = LocalDateTime.now();

        record.logActivity("Tomar Sol", null, start, start.plusHours(1), STAFF_ID, null);
        record.logActivity("Clube de Leitura", null, start.plusHours(2), start.plusHours(3), STAFF_ID, null);

        assertEquals(2, record.getHistory().size());
    }
}
