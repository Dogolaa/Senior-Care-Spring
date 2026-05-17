package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.PrescriptionModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataPrescriptionRepository extends JpaRepository<PrescriptionModel, UUID> {

    List<PrescriptionModel> findByMedicalRecordId(UUID medicalRecordId);
}
