package org.seniorcare.health.domain.repositories;

import org.seniorcare.health.domain.entities.Medication;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicationRepository {
    Optional<Medication> findById(UUID id);
    List<Medication> search(String name, int page, int count);
}
