package org.seniorcare.identityaccess.domain.entities;

import java.util.UUID;

public class User {

    private final UUID id;
    private String name;
    private String email;
    private String phone;
    private boolean isActive;
    private UUID addressId;
    private String password;
    private UUID roleId;

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
    }

    public static User create(String name, String email, String phone, UUID addressId, String plainTextPassword, UUID roleId) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
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


    public void deactivate() {
        if (!this.isActive) {
            throw new IllegalStateException("User is already inactive.");
        }
        this.isActive = false;
    }

    public void activate() {
        if (this.isActive) {
            throw new IllegalStateException("User is already active.");
        }
        this.isActive = true;
    }

    public void changePassword(String newPlainTextPassword) {
        if (newPlainTextPassword == null || newPlainTextPassword.length() < 8) {
            throw new IllegalArgumentException("New password must be at least 8 characters long.");
        }
        this.password = newPlainTextPassword;
    }

    public void updateProfile(String newName, String newPhone) {
        if (newName == null || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be empty.");
        }
        this.name = newName;
        this.phone = newPhone;
    }


    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    public boolean isActive() { return isActive; }
    public UUID getAddressId() { return addressId; }
    public String getPassword() { return password; }
    public UUID getRoleId() { return roleId; }
}