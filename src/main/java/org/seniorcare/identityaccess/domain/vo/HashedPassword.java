package org.seniorcare.identityaccess.domain.vo;

import org.seniorcare.shared.exceptions.BadRequestException;

public record HashedPassword(String value) {
    public HashedPassword {
        if (value == null || value.isBlank()) {
            throw new BadRequestException("Hashed password value cannot be null or blank.");
        }
        if (!value.startsWith("$2")) {
            throw new IllegalArgumentException("HashedPassword VO must only receive hashed passwords.");
        }
    }
}