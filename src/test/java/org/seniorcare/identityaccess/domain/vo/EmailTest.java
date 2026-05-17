package org.seniorcare.identityaccess.domain.vo;

import org.junit.jupiter.api.Test;
import org.seniorcare.shared.exceptions.BadRequestException;

import static org.junit.jupiter.api.Assertions.*;

class EmailTest {

    @Test
    void validEmail_shouldBeCreatedSuccessfully() {
        assertDoesNotThrow(() -> new Email("usuario@example.com"));
    }

    @Test
    void validEmailWithSubdomain_shouldBeCreatedSuccessfully() {
        assertDoesNotThrow(() -> new Email("usuario@mail.example.com.br"));
    }

    @Test
    void nullEmail_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Email(null));
    }

    @Test
    void emailWithoutAtSign_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Email("emailinvalido.com"));
    }

    @Test
    void emailWithoutDomain_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Email("email@"));
    }

    @Test
    void emailWithoutLocalPart_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Email("@example.com"));
    }

    @Test
    void emailWithSpaces_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () -> new Email("usuario @example.com"));
    }

    @Test
    void value_shouldReturnEmailString() {
        Email email = new Email("usuario@example.com");
        assertEquals("usuario@example.com", email.value());
    }
}
