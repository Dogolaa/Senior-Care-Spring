package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataActivityRecordHistoryRepository extends JpaRepository<ActivityRecordHistoryModel, UUID> {
}
