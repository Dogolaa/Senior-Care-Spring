package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.entities.ActivityRecordPhoto;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.health.infrastructure.persistence.jpa.mappers.ActivityRecordMapper;
import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordHistoryModel;
import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordModel;
import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordPhotoModel;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ActivityRecordRepositoryImpl implements IActivityRecordRepository {

    private final SpringDataActivityRecordRepository jpaRepository;
    private final SpringDataActivityRecordHistoryRepository historyJpaRepository;
    private final SpringDataActivityRecordPhotoRepository photoJpaRepository;
    private final ActivityRecordMapper mapper;

    public ActivityRecordRepositoryImpl(
            SpringDataActivityRecordRepository jpaRepository,
            SpringDataActivityRecordHistoryRepository historyJpaRepository,
            SpringDataActivityRecordPhotoRepository photoJpaRepository,
            ActivityRecordMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.historyJpaRepository = historyJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public ActivityRecord save(ActivityRecord record) {
        ActivityRecordModel model = mapper.toModel(record);
        model.getHistory().forEach(h -> h.setActivityRecord(model));
        ActivityRecordModel saved = jpaRepository.save(model);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<ActivityRecord> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<ActivityRecord> findByResidentId(UUID residentId) {
        return jpaRepository.findByResidentId(residentId).map(mapper::toDomain);
    }

    @Override
    public void addPhotoToHistory(UUID activityRecordHistoryId, ActivityRecordPhoto photo) {
        ActivityRecordHistoryModel history = historyJpaRepository.findById(activityRecordHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity record history not found: " + activityRecordHistoryId));

        ActivityRecordPhotoModel photoModel = new ActivityRecordPhotoModel();
        photoModel.setId(photo.getId());
        photoModel.setActivityRecordHistory(history);
        photoModel.setPhotoUrl(photo.getPhotoUrl());
        photoModel.setUploadedAt(photo.getUploadedAt());
        photoJpaRepository.save(photoModel);
    }
}
