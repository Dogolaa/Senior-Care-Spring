package org.seniorcare.incidents.domain.repositories;

import org.seniorcare.incidents.domain.aggregates.Incident;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IIncidentRepository {
    Incident save(Incident incident);
    Optional<Incident> findById(UUID id);
    List<Incident> findByResidentId(UUID residentId);
    void deleteById(UUID id);
}
