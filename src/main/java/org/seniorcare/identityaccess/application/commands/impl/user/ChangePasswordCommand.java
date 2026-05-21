package org.seniorcare.identityaccess.application.commands.impl.user;

import java.util.UUID;

public record ChangePasswordCommand(UUID userId, String newPassword) {
}
