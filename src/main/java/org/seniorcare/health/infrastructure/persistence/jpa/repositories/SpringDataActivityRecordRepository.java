package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataActivityRecordRepository extends JpaRepository<ActivityRecordModel, UUID> {
    Optional<ActivityRecordModel> findByResidentId(UUID residentId);
}
