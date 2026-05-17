package org.seniorcare.identityaccess.domain.entities;

import org.junit.jupiter.api.Test;
import org.seniorcare.identityaccess.domain.vo.HashedPassword;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    private static final UUID ROLE_ID = UUID.randomUUID();
    private static final HashedPassword HASHED_PASSWORD = new HashedPassword("$2a$10$hashed");

    @Test
    void create_withValidData_shouldReturnActiveUser() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        assertNotNull(user.getId());
        assertEquals("João Silva", user.getName());
        assertEquals("joao@example.com", user.getEmail().value());
        assertEquals(ROLE_ID, user.getRoleId());
        assertTrue(user.isActive());
        assertNull(user.getDeletedAt());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void create_withNullName_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () ->
                User.create(null, "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID));
    }

    @Test
    void create_withBlankName_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () ->
                User.create("   ", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID));
    }

    @Test
    void create_withNullHashedPassword_shouldThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                User.create("João", "joao@example.com", "11999999999", null, null, ROLE_ID));
    }

    @Test
    void create_withNullRoleId_shouldThrowIllegalArgument() {
        assertThrows(IllegalArgumentException.class, () ->
                User.create("João", "joao@example.com", "11999999999", null, HASHED_PASSWORD, null));
    }

    @Test
    void create_withInvalidEmail_shouldThrowBadRequest() {
        assertThrows(BadRequestException.class, () ->
                User.create("João", "email-invalido", "11999999999", null, HASHED_PASSWORD, ROLE_ID));
    }

    @Test
    void update_withValidData_shouldUpdateFields() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);
        UUID newRoleId = UUID.randomUUID();

        user.update("João Atualizado", "novo@example.com", "11888888888", null, newRoleId);

        assertEquals("João Atualizado", user.getName());
        assertEquals("novo@example.com", user.getEmail().value());
        assertEquals(newRoleId, user.getRoleId());
    }

    @Test
    void update_withBlankName_shouldThrowBadRequest() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        assertThrows(BadRequestException.class, () ->
                user.update("", "novo@example.com", "11888888888", null, ROLE_ID));
    }

    @Test
    void update_withNullRoleId_shouldThrowBadRequest() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        assertThrows(BadRequestException.class, () ->
                user.update("João", "novo@example.com", "11888888888", null, null));
    }

    @Test
    void softDelete_shouldSetDeletedAt() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        user.softDelete();

        assertNotNull(user.getDeletedAt());
    }

    @Test
    void softDelete_alreadyDeleted_shouldThrowResourceNotFound() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);
        user.softDelete();

        assertThrows(ResourceNotFoundException.class, user::softDelete);
    }

    @Test
    void changeRole_withValidRoleId_shouldUpdateRole() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);
        UUID newRoleId = UUID.randomUUID();

        user.changeRole(newRoleId);

        assertEquals(newRoleId, user.getRoleId());
    }

    @Test
    void changeRole_withNullRoleId_shouldThrowBadRequest() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        assertThrows(BadRequestException.class, () -> user.changeRole(null));
    }

    @Test
    void changePassword_withValidPassword_shouldUpdate() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);
        HashedPassword newPassword = new HashedPassword("$2a$10$newHash");

        user.changePassword(newPassword);

        assertEquals("$2a$10$newHash", user.getPassword().value());
    }

    @Test
    void changePassword_withNull_shouldThrowIllegalArgument() {
        User user = User.create("João Silva", "joao@example.com", "11999999999", null, HASHED_PASSWORD, ROLE_ID);

        assertThrows(IllegalArgumentException.class, () -> user.changePassword(null));
    }
}
