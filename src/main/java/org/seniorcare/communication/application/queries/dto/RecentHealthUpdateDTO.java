package org.seniorcare.communication.application.queries.dto;

import java.time.LocalDate;
import java.util.UUID;

public record RecentHealthUpdateDTO(UUID residentId, String residentName, String room, LocalDate lastUpdated) {
}
