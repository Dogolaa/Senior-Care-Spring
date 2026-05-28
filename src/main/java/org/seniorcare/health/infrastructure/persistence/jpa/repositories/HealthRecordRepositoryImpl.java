package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.entities.HealthRecordPhoto;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.health.infrastructure.persistence.jpa.mappers.HealthRecordMapper;
import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordHistoryModel;
import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordModel;
import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordPhotoModel;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class HealthRecordRepositoryImpl implements IHealthRecordRepository {

    private final HealthRecordJpaRepository jpaRepository;
    private final SpringDataHealthRecordHistoryRepository historyJpaRepository;
    private final SpringDataHealthRecordPhotoRepository photoJpaRepository;
    private final HealthRecordMapper mapper;

    public HealthRecordRepositoryImpl(
            HealthRecordJpaRepository jpaRepository,
            SpringDataHealthRecordHistoryRepository historyJpaRepository,
            SpringDataHealthRecordPhotoRepository photoJpaRepository,
            HealthRecordMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.historyJpaRepository = historyJpaRepository;
        this.photoJpaRepository = photoJpaRepository;
        this.mapper = mapper;
    }

    @Override
    public HealthRecord save(HealthRecord healthRecord) {
        HealthRecordModel model = mapper.toModel(healthRecord);
        model.getHistory().forEach(h -> h.setHealthRecord(model));
        HealthRecordModel savedModel = jpaRepository.save(model);
        return mapper.toDomain(savedModel);
    }

    @Override
    public Optional<HealthRecord> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<HealthRecord> findByResidentId(UUID residentId) {
        return jpaRepository.findByResidentId(residentId).map(mapper::toDomain);
    }

    @Override
    public void addPhotoToHistory(UUID healthRecordHistoryId, HealthRecordPhoto photo) {
        HealthRecordHistoryModel history = historyJpaRepository.findById(healthRecordHistoryId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record history not found: " + healthRecordHistoryId));

        HealthRecordPhotoModel photoModel = new HealthRecordPhotoModel();
        photoModel.setId(photo.getId());
        photoModel.setHealthRecordHistory(history);
        photoModel.setPhotoUrl(photo.getPhotoUrl());
        photoModel.setUploadedAt(photo.getUploadedAt());
        photoJpaRepository.save(photoModel);
    }
}
