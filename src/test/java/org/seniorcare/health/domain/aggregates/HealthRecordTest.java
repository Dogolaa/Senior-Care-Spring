package org.seniorcare.health.domain.aggregates;

import org.junit.jupiter.api.Test;
import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HealthRecordTest {

    private static final UUID RESIDENT_ID = UUID.randomUUID();
    private static final UUID STAFF_ID = UUID.randomUUID();

    @Test
    void create_shouldCalculateImcAndAddHistoryEntry() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        float expectedImc = 70f / (1.75f * 1.75f);
        assertEquals(expectedImc, record.getImc(), 0.001f);
        assertEquals(1, record.getHistory().size());
        assertEquals(VitalSignsSource.MANUAL, record.getHistory().get(0).getSource());
    }

    @Test
    void create_withNullHeight_shouldReturnNullImc() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, null, 70f, "120/80", 72, 36.5f, 98f);

        assertNull(record.getImc());
    }

    @Test
    void create_withNullWeight_shouldReturnNullImc() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, null, "120/80", 72, 36.5f, 98f);

        assertNull(record.getImc());
    }

    @Test
    void create_withZeroHeight_shouldReturnNullImc() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 0f, 70f, "120/80", 72, 36.5f, 98f);

        assertNull(record.getImc());
    }

    @Test
    void update_shouldUpdateFieldsAndAddHistoryEntry() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        UUID newStaffId = UUID.randomUUID();
        record.update(newStaffId, 1.75f, 80f, "130/85", 75, 37.0f, 97f);

        assertEquals(80f, record.getWeight());
        assertEquals("130/85", record.getBloodPressure());
        assertEquals(75, record.getHeartRate());
        assertEquals(2, record.getHistory().size());
        float expectedImc = 80f / (1.75f * 1.75f);
        assertEquals(expectedImc, record.getImc(), 0.001f);
    }

    @Test
    void recordVitals_withPartialData_shouldOnlyUpdateProvidedFields() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, 70f, "120/80", 72, 36.5f, 98f);
        String originalBloodPressure = record.getBloodPressure();
        Float originalTemperature = record.getTemperature();

        record.recordVitals(STAFF_ID, 80, 99f, null, null, VitalSignsSource.WEARABLE);

        assertEquals(80, record.getHeartRate());
        assertEquals(99f, record.getSaturation());
        assertEquals(originalBloodPressure, record.getBloodPressure());
        assertEquals(originalTemperature, record.getTemperature());
        assertEquals(2, record.getHistory().size());
        assertEquals(VitalSignsSource.WEARABLE, record.getHistory().get(1).getSource());
    }

    @Test
    void recordVitals_withMedicalDevice_shouldAddHistoryWithCorrectSource() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        record.recordVitals(STAFF_ID, null, null, "140/90", 38.0f, VitalSignsSource.MEDICAL_DEVICE);

        assertEquals("140/90", record.getBloodPressure());
        assertEquals(38.0f, record.getTemperature());
        assertEquals(VitalSignsSource.MEDICAL_DEVICE, record.getHistory().get(1).getSource());
    }

    @Test
    void multipleUpdates_shouldAccumulateHistoryEntries() {
        HealthRecord record = new HealthRecord(RESIDENT_ID, STAFF_ID, 1.75f, 70f, "120/80", 72, 36.5f, 98f);

        record.update(STAFF_ID, 1.75f, 71f, "121/81", 73, 36.6f, 97f);
        record.update(STAFF_ID, 1.75f, 72f, "122/82", 74, 36.7f, 96f);

        assertEquals(3, record.getHistory().size());
    }
}
