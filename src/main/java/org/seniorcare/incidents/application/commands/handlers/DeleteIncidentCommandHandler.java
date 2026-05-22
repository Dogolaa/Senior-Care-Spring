package org.seniorcare.incidents.application.commands.handlers;

import org.seniorcare.incidents.application.commands.impl.DeleteIncidentCommand;
import org.seniorcare.incidents.domain.repositories.IIncidentRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteIncidentCommandHandler {

    private final IIncidentRepository incidentRepository;

    public DeleteIncidentCommandHandler(IIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional
    public void handle(DeleteIncidentCommand command) {
        incidentRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found: " + command.id()));
        incidentRepository.deleteById(command.id());
    }
}
