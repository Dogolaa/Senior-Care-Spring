package org.seniorcare.health.application.queries.impl;

import java.util.UUID;

public record FindPrescriptionsByHealthRecordIdQuery(UUID healthRecordId) {
}
