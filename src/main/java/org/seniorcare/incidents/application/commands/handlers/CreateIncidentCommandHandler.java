package org.seniorcare.incidents.application.commands.handlers;

import org.seniorcare.incidents.application.commands.impl.CreateIncidentCommand;
import org.seniorcare.incidents.application.ports.output.IIncidentAlertPort;
import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.domain.repositories.IIncidentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateIncidentCommandHandler {

    private final IIncidentRepository incidentRepository;
    private final IIncidentAlertPort alertPort;

    public CreateIncidentCommandHandler(IIncidentRepository incidentRepository, IIncidentAlertPort alertPort) {
        this.incidentRepository = incidentRepository;
        this.alertPort = alertPort;
    }

    @Transactional
    public UUID handle(CreateIncidentCommand command) {
        Incident incident = Incident.report(
                command.residentId(),
                command.reportedById(),
                command.incidentType(),
                command.severity(),
                command.description(),
                command.actionTaken(),
                command.occurredAt(),
                command.room()
        );
        Incident saved = incidentRepository.save(incident);
        alertPort.notifyFamilyMembers(saved);
        return saved.getId();
    }
}
