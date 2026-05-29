package org.seniorcare.identityaccess.application.dto.auth;

import java.util.UUID;

public record LoginResult(
        String token,
        UUID userId,
        String name,
        String email,
        String role,
        boolean mustChangePassword
) {}
