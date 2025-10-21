package org.seniorcare.identityaccess.domain.vo;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.util.regex.Pattern;

public record Password(String plainTextValue) {

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,72}$");

    public Password {
        if (plainTextValue == null || plainTextValue.isBlank()) {
            throw new BadRequestException("Password cannot be empty.");
        }

        if (!PASSWORD_PATTERN.matcher(plainTextValue).matches()) {
            throw new BadRequestException(
                    "Password does not meet complexity requirements. " +
                            "It must be between 8 and 72 characters, " +
                            "contain at least one uppercase letter, " +
                            "one lowercase letter, one number, " +
                            "and one special character (@#$%^&+=!)."
            );
        }
    }

    public String value() {
        return plainTextValue;
    }
}