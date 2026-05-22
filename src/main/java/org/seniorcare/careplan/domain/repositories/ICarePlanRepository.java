package org.seniorcare.careplan.domain.repositories;

import org.seniorcare.careplan.domain.aggregates.CarePlan;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ICarePlanRepository {

    CarePlan save(CarePlan carePlan);

    Optional<CarePlan> findById(UUID id);

    List<CarePlan> findByResidentId(UUID residentId);

    void deleteById(UUID id);
}
