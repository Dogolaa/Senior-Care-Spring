package org.seniorcare.careplan.application.commands.handlers;

import org.seniorcare.careplan.application.commands.impl.CreateCarePlanCommand;
import org.seniorcare.careplan.domain.aggregates.CarePlan;
import org.seniorcare.careplan.domain.repositories.ICarePlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateCarePlanCommandHandler {

    private final ICarePlanRepository repository;

    public CreateCarePlanCommandHandler(ICarePlanRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UUID handle(CreateCarePlanCommand command) {
        CarePlan plan = CarePlan.create(
                command.residentId(),
                command.responsibleId(),
                command.title(),
                command.description(),
                command.goals(),
                command.interventions(),
                command.startDate(),
                command.endDate()
        );
        return repository.save(plan).getId();
    }
}
