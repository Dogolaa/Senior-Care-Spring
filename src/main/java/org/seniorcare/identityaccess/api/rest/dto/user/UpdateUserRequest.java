package org.seniorcare.identityaccess.api.rest.dto.user;

import java.util.UUID;

public record UpdateUserRequest(
        String name,
        String email,
        String phone,
        Boolean isActive,
        UUID addressId,
        UUID roleId
) {
}
