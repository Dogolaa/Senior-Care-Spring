package org.seniorcare.identityaccess.application.commands.impl.user;

import java.util.UUID;

public record CreateUserCommand(
        String name,
        String email,
        String phone,
        UUID addressId,
        String plainTextPassword) {
}

