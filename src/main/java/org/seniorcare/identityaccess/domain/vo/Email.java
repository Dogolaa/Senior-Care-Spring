package org.seniorcare.identityaccess.domain.vo;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.util.regex.Pattern;

public record Email(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
    );

    public Email {
        if (value == null || !EMAIL_PATTERN.matcher(value).matches()) {
            throw new BadRequestException("Invalid email format for: " + value);
        }
    }
}
