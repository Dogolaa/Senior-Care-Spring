package org.seniorcare.identityaccess.api.rest.dto.user;

import java.util.UUID;

public record CreateUserRequest(
        String name,
        String email,
        String phone,
        UUID addressId,
        String password
) {
}