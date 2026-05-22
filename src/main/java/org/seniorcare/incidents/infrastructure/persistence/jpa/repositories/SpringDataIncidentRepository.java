package org.seniorcare.incidents.infrastructure.persistence.jpa.repositories;

import org.seniorcare.incidents.infrastructure.persistence.jpa.models.IncidentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataIncidentRepository extends JpaRepository<IncidentModel, UUID> {

    List<IncidentModel> findByResidentId(UUID residentId);
}
