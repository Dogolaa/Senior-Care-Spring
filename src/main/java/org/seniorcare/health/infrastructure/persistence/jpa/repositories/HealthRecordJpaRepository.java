package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import java.util.Optional;
import java.util.UUID;

import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HealthRecordJpaRepository extends JpaRepository<HealthRecordModel, UUID> {
    Optional<HealthRecordModel> findByResidentId(UUID residentId);
}
