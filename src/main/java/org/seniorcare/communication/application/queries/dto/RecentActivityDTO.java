package org.seniorcare.communication.application.queries.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record RecentActivityDTO(UUID residentId, String residentName, String room, String activityName, LocalDateTime conductedAt) {
}
