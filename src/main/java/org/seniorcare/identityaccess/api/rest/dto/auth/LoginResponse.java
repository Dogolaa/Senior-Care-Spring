package org.seniorcare.identityaccess.api.rest.dto.auth;

import java.util.UUID;

public record LoginResponse(
        String token,
        UUID userId,
        String name,
        String email,
        String role
) {
}