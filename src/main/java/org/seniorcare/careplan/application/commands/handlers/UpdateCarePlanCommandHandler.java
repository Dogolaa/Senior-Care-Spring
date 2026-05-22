package org.seniorcare.careplan.application.commands.handlers;

import org.seniorcare.careplan.application.commands.impl.UpdateCarePlanCommand;
import org.seniorcare.careplan.domain.aggregates.CarePlan;
import org.seniorcare.careplan.domain.repositories.ICarePlanRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateCarePlanCommandHandler {

    private final ICarePlanRepository repository;

    public UpdateCarePlanCommandHandler(ICarePlanRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(UpdateCarePlanCommand command) {
        CarePlan plan = repository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Care plan not found: " + command.id()));

        plan.update(
                command.title(),
                command.description(),
                command.goals(),
                command.interventions(),
                command.startDate(),
                command.endDate(),
                command.status()
        );

        repository.save(plan);
    }
}
