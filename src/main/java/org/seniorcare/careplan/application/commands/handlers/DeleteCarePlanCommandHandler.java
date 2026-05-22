package org.seniorcare.careplan.application.commands.handlers;

import org.seniorcare.careplan.application.commands.impl.DeleteCarePlanCommand;
import org.seniorcare.careplan.domain.repositories.ICarePlanRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteCarePlanCommandHandler {

    private final ICarePlanRepository repository;

    public DeleteCarePlanCommandHandler(ICarePlanRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void handle(DeleteCarePlanCommand command) {
        repository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Care plan not found: " + command.id()));
        repository.deleteById(command.id());
    }
}
