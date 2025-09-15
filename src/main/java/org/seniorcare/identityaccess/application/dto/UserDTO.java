package org.seniorcare.identityaccess.application.dto;

import java.util.UUID;

public record UserDTO(

        UUID id,
        String name,
        String email,
        String phone,
        boolean isActive
) {
}


