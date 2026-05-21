package org.seniorcare.identityaccess.application.commands.impl.user;

import java.util.UUID;

public record UpdateUserPhotoCommand(UUID userId, String photoUrl) {
}
