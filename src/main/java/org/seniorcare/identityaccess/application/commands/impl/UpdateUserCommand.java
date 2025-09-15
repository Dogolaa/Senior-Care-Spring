package org.seniorcare.identityaccess.application.commands.impl;

import java.util.UUID;

public record UpdateUserCommand(
        UUID id,
        String name,
        String email,
        String phone,
        Boolean isActive,
        UUID addressId,
        UUID roleId) {
}

