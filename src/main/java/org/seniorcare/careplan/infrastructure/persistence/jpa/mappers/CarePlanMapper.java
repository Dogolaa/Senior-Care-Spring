package org.seniorcare.careplan.infrastructure.persistence.jpa.mappers;

import org.seniorcare.careplan.domain.aggregates.CarePlan;
import org.seniorcare.careplan.infrastructure.persistence.jpa.models.CarePlanModel;
import org.springframework.stereotype.Component;

@Component
public class CarePlanMapper {

    public CarePlanModel toModel(CarePlan plan) {
        CarePlanModel model = new CarePlanModel();
        model.setId(plan.getId());
        model.setResidentId(plan.getResidentId());
        model.setResponsibleId(plan.getResponsibleId());
        model.setTitle(plan.getTitle());
        model.setDescription(plan.getDescription());
        model.setGoals(plan.getGoals());
        model.setInterventions(plan.getInterventions());
        model.setStartDate(plan.getStartDate());
        model.setEndDate(plan.getEndDate());
        model.setStatus(plan.getStatus());
        model.setCreatedAt(plan.getCreatedAt());
        model.setUpdatedAt(plan.getUpdatedAt());
        return model;
    }

    public CarePlan toEntity(CarePlanModel model) {
        return new CarePlan(
                model.getId(),
                model.getResidentId(),
                model.getResponsibleId(),
                model.getTitle(),
                model.getDescription(),
                model.getGoals(),
                model.getInterventions(),
                model.getStartDate(),
                model.getEndDate(),
                model.getStatus(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}
