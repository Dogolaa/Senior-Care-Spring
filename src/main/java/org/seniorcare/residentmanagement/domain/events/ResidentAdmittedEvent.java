package org.seniorcare.residentmanagement.domain.events;

import java.time.Instant;
import java.util.UUID;

public record ResidentAdmittedEvent(
        UUID residentId,
        String residentName,
        Instant occurredOn
) {

    public ResidentAdmittedEvent(UUID residentId, String residentName) {
        this(residentId, residentName, Instant.now());
    }
}