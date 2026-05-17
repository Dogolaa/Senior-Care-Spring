package org.seniorcare.health.domain.repositories;

import org.seniorcare.health.domain.aggregates.HealthRecord;

import java.util.Optional;
import java.util.UUID;

public interface IHealthRecordRepository {
    HealthRecord save(HealthRecord healthRecord);
    Optional<HealthRecord> findById(UUID id);
    Optional<HealthRecord> findByResidentId(UUID residentId);
    void addPhotoToHistory(UUID healthRecordHistoryId, String photoUrl);
}
