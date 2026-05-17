package org.seniorcare.residentmanagement.application.commands.impl.resident;

import java.util.UUID;

public record AddAllergyCommand(UUID residentId, String allergyDescription) {
}
