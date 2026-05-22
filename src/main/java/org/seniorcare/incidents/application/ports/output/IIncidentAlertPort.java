package org.seniorcare.incidents.application.ports.output;

import org.seniorcare.incidents.domain.aggregates.Incident;

public interface IIncidentAlertPort {

    void notifyFamilyMembers(Incident incident);
}
