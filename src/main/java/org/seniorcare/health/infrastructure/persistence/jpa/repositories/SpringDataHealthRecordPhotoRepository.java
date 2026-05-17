package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.HealthRecordPhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataHealthRecordPhotoRepository extends JpaRepository<HealthRecordPhotoModel, UUID> {
}
