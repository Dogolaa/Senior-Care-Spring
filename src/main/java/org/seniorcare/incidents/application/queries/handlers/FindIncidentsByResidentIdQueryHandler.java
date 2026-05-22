package org.seniorcare.incidents.application.queries.handlers;

import org.seniorcare.incidents.application.queries.dto.IncidentResponse;
import org.seniorcare.incidents.application.queries.impl.FindIncidentsByResidentIdQuery;
import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.domain.repositories.IIncidentRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FindIncidentsByResidentIdQueryHandler {

    private final IIncidentRepository incidentRepository;

    public FindIncidentsByResidentIdQueryHandler(IIncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public List<IncidentResponse> handle(FindIncidentsByResidentIdQuery query) {
        return incidentRepository.findByResidentId(query.residentId())
                .stream()
                .sorted(Comparator.comparing(Incident::getOccurredAt).reversed())
                .map(i -> new IncidentResponse(
                        i.getId(),
                        i.getResidentId(),
                        i.getReportedById(),
                        i.getIncidentType(),
                        i.getSeverity(),
                        i.getDescription(),
                        i.getActionTaken(),
                        i.getOccurredAt(),
                        i.getRoom(),
                        i.getCreatedAt(),
                        i.getUpdatedAt()
                ))
                .toList();
    }
}
