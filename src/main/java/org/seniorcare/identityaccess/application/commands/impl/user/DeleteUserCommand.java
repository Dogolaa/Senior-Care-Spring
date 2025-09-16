package org.seniorcare.identityaccess.application.commands.impl.user;

import java.util.UUID;

public record DeleteUserCommand(
        UUID id) {
}

