package org.seniorcare.residentmanagement.application.commands.impl.resident;

import java.util.UUID;

public record RemoveAllergyCommand(UUID residentId, String allergyDescription) {
}
