package org.seniorcare.careplan.application.queries.handlers;

import org.seniorcare.careplan.application.queries.dto.CarePlanResponse;
import org.seniorcare.careplan.application.queries.impl.FindCarePlansByResidentIdQuery;
import org.seniorcare.careplan.domain.aggregates.CarePlan;
import org.seniorcare.careplan.domain.repositories.ICarePlanRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FindCarePlansByResidentIdQueryHandler {

    private final ICarePlanRepository repository;

    public FindCarePlansByResidentIdQueryHandler(ICarePlanRepository repository) {
        this.repository = repository;
    }

    public List<CarePlanResponse> handle(FindCarePlansByResidentIdQuery query) {
        return repository.findByResidentId(query.residentId())
                .stream()
                .sorted(Comparator.comparing(CarePlan::getCreatedAt).reversed())
                .map(p -> new CarePlanResponse(
                        p.getId(),
                        p.getResidentId(),
                        p.getResponsibleId(),
                        p.getTitle(),
                        p.getDescription(),
                        p.getGoals(),
                        p.getInterventions(),
                        p.getStartDate(),
                        p.getEndDate(),
                        p.getStatus(),
                        p.getCreatedAt(),
                        p.getUpdatedAt()
                ))
                .toList();
    }
}
