package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record AddPhotoToActivityHistoryCommand(
        UUID activityRecordHistoryId,
        byte[] fileContent,
        String filename,
        String contentType
) {
}
