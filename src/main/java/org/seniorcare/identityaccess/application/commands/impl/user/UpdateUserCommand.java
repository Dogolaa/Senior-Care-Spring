package org.seniorcare.identityaccess.application.commands.impl.user;

import java.util.UUID;

public record UpdateUserCommand(
        UUID id,
        String name,
        String email,
        String phone,
        UUID addressId,
        UUID roleId) {
}

