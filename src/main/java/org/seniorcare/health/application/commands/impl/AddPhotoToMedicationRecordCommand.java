package org.seniorcare.health.application.commands.impl;

import java.util.UUID;

public record AddPhotoToMedicationRecordCommand(UUID medicationRecordId, String photoUrl) {
}
