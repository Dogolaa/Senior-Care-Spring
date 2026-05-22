package org.seniorcare.incidents.application.commands.handlers;

import org.seniorcare.incidents.application.commands.impl.UpdateIncidentCommand;
import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.domain.repositories.IIncidentRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateIncidentCommandHandler {

    private final IIncidentRepository incidentRepository;

    public UpdateIncidentCommandHandler(IIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    @Transactional
    public void handle(UpdateIncidentCommand command) {
        Incident incident = incidentRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Incident not found: " + command.id()));

        incident.update(
                command.incidentType(),
                command.severity(),
                command.description(),
                command.actionTaken(),
                command.occurredAt(),
                command.room()
        );
        incidentRepository.save(incident);
    }
}
