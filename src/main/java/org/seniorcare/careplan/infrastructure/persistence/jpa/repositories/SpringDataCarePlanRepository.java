package org.seniorcare.careplan.infrastructure.persistence.jpa.repositories;

import org.seniorcare.careplan.infrastructure.persistence.jpa.models.CarePlanModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface SpringDataCarePlanRepository extends JpaRepository<CarePlanModel, UUID> {

    List<CarePlanModel> findByResidentId(UUID residentId);
}
