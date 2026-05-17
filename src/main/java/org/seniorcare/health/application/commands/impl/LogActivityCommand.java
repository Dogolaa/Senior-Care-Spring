package org.seniorcare.health.application.commands.impl;

import java.time.LocalDateTime;
import java.util.UUID;

public record LogActivityCommand(
        UUID activityRecordId,
        String activityName,
        String description,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        UUID conductedById,
        String notes
) {
}
