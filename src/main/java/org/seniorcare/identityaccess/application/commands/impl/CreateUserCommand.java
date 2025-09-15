package org.seniorcare.identityaccess.application.commands.impl;

import java.util.UUID;

public record CreateUserCommand(
        String name,
        String email,
        String phone,
        UUID addressId,
        String plainTextPassword,
        UUID roleId) {
}

