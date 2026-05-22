package org.seniorcare.incidents.infrastructure.persistence.jpa.mappers;

import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.infrastructure.persistence.jpa.models.IncidentModel;
import org.springframework.stereotype.Component;

@Component
public class IncidentMapper {

    public IncidentModel toModel(Incident incident) {
        IncidentModel model = new IncidentModel();
        model.setId(incident.getId());
        model.setResidentId(incident.getResidentId());
        model.setReportedById(incident.getReportedById());
        model.setIncidentType(incident.getIncidentType());
        model.setSeverity(incident.getSeverity());
        model.setDescription(incident.getDescription());
        model.setActionTaken(incident.getActionTaken());
        model.setOccurredAt(incident.getOccurredAt());
        model.setRoom(incident.getRoom());
        model.setCreatedAt(incident.getCreatedAt());
        model.setUpdatedAt(incident.getUpdatedAt());
        return model;
    }

    public Incident toEntity(IncidentModel model) {
        return new Incident(
                model.getId(),
                model.getResidentId(),
                model.getReportedById(),
                model.getIncidentType(),
                model.getSeverity(),
                model.getDescription(),
                model.getActionTaken(),
                model.getOccurredAt(),
                model.getRoom(),
                model.getCreatedAt(),
                model.getUpdatedAt()
        );
    }
}
