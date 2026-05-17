package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordHistoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataHealthRecordHistoryRepository extends JpaRepository<HealthRecordHistoryModel, UUID> {
}
