package org.seniorcare.identityaccess.api.rest.dto;

import java.util.UUID;

public record UpdateUserRequest(
        String name,
        String email,
        String phone,
        UUID addressId,
        UUID roleId
) {
}
