package org.seniorcare.health.domain.repositories;

import org.seniorcare.health.domain.entities.Prescription;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPrescriptionRepository {
    Prescription save(Prescription prescription);
    Optional<Prescription> findById(UUID id);
    List<Prescription> findByHealthRecordId(UUID healthRecordId);
}
