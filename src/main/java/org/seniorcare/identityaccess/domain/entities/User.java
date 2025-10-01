package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.util.UUID;

public class User {

    private final UUID id;
    private String name;
    private String email;
    private String phone;
    private Boolean isActive;
    private UUID addressId;
    private String password;
    private UUID roleId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    // TODO: [DDD] Refatorar 'email' para um Value Object 'Email' com validação de formato.
    // TODO: [SECURITY] Refatorar 'password' para um Value Object 'Password' com hashing do Spring Security.

    private User(UUID id, String name, String email, String phone, UUID addressId, String password, UUID roleId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = true;
        this.addressId = addressId;
        this.password = password;
        this.roleId = roleId;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.deletedAt = null;
    }

    public User(UUID id, String name, String email, String phone, Boolean isActive, UUID addressId, String password, UUID roleId, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.addressId = addressId;
        this.password = password;
        this.roleId = roleId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public boolean isActive() {
        return isActive;
    }

    public UUID getAddressId() {
        return addressId;
    }

    public String getPassword() {
        return password;
    }

    public UUID getRoleId() {
        return roleId;
    }


    public static User create(String name, String email, String phone, UUID addressId, String plainTextPassword, UUID roleId) {
        if (name == null || name.trim().isEmpty()) {
            throw new BadRequestException("User name cannot be empty.");
        }
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("A valid email is required.");
        }
        if (plainTextPassword == null || plainTextPassword.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long.");
        }
        if (roleId == null) {
            throw new IllegalArgumentException("User must have a role.");
        }

        return new User(UUID.randomUUID(), name, email, phone, addressId, plainTextPassword, roleId);
    }


    public void changePassword(String newPlainTextPassword) {
        if (newPlainTextPassword == null || newPlainTextPassword.length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters long.");
        }
        this.password = newPlainTextPassword;
        this.updatedAt = Instant.now();
    }

    public void update(String newName, String newEmail, String newPhone, UUID newAddressId, UUID newRoleId) {

        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }
        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("A valid email is required.");
        }
        if (newRoleId == null) {
            throw new IllegalArgumentException("User must have a role.");
        }

        this.name = newName;
        this.email = newEmail;
        this.phone = newPhone;
        this.addressId = newAddressId;
        this.roleId = newRoleId;

        this.updatedAt = Instant.now();
    }

    public void softDelete() {
        if (this.deletedAt != null) {
            throw new IllegalStateException("User is already deleted.");
        }
        this.deletedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}