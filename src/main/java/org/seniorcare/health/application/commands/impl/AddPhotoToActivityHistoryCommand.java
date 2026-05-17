package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record AddPhotoToActivityHistoryCommand(
        UUID activityRecordHistoryId,
        String photoUrl
) {
}
