package org.seniorcare.identityaccess.domain.vo;

import org.junit.jupiter.api.Test;
import org.seniorcare.shared.exceptions.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {

    @Test
    void validPassword_shouldBeCreatedSuccessfully() {
        assertDoesNotThrow(() -> new Password("Senha@123"));
    }

    @Test
    void validPasswordWithSpecialChars_shouldBeCreatedSuccessfully() {
        assertDoesNotThrow(() -> new Password("M1nh@Senha!"));
    }

    @Test
    void nullPassword_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password(null));
    }

    @Test
    void blankPassword_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("   "));
    }

    @Test
    void passwordWithoutUppercase_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("senha@123"));
    }

    @Test
    void passwordWithoutLowercase_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("SENHA@123"));
    }

    @Test
    void passwordWithoutNumber_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("Senha@abc"));
    }

    @Test
    void passwordWithoutSpecialChar_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("Senha1234"));
    }

    @Test
    void passwordTooShort_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("S1@a"));
    }

    @Test
    void passwordWithSpaces_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Password("Senha @123"));
    }

    @Test
    void value_shouldReturnPlainText() {
        Password password = new Password("Senha@123");
        assertEquals("Senha@123", password.value());
    }
}
