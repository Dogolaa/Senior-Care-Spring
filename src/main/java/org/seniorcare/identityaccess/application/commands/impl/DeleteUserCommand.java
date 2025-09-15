package org.seniorcare.identityaccess.application.commands.impl;

import java.util.UUID;

public record DeleteUserCommand(
        UUID id) {
}

