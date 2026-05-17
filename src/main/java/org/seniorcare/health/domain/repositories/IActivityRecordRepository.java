package org.seniorcare.health.domain.repositories;

import org.seniorcare.health.domain.aggregates.ActivityRecord;

import java.util.Optional;
import java.util.UUID;

public interface IActivityRecordRepository {
    ActivityRecord save(ActivityRecord record);
    Optional<ActivityRecord> findById(UUID id);
    Optional<ActivityRecord> findByResidentId(UUID residentId);
    void addPhotoToHistory(UUID activityRecordHistoryId, String photoUrl);
}
