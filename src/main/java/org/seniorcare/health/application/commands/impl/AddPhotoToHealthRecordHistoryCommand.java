package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record AddPhotoToHealthRecordHistoryCommand(
        UUID healthRecordHistoryId,
        String photoUrl
) {
}
