package org.seniorcare.residentmanagement.application.commands.impl.familyLink;

import java.util.UUID;

public record AddFamilyLinkCommand(
        UUID residentId,
        UUID familyMemberId,
        String relationship,
        boolean isPrimaryContact
) {
}