package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.ActivityRecordPhotoModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataActivityRecordPhotoRepository extends JpaRepository<ActivityRecordPhotoModel, UUID> {
}
