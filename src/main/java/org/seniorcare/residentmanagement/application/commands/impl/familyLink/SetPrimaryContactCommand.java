package org.seniorcare.residentmanagement.application.commands.impl.familyLink;

import java.util.UUID;

public record SetPrimaryContactCommand(UUID residentId, UUID familyLinkId) {
}
