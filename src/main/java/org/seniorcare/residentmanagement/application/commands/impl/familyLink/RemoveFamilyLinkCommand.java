package org.seniorcare.residentmanagement.application.commands.impl.familyLink;

import java.util.UUID;

public record RemoveFamilyLinkCommand(UUID residentId, UUID familyLinkId) {
}
