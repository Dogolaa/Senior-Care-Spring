package org.seniorcare.incidents.application.queries.impl;

import java.util.UUID;

public record FindIncidentsByResidentIdQuery(UUID residentId) {
}
