package org.seniorcare.identityaccess.domain.entities;

import java.util.UUID;

public class Permission {

    private final UUID id;
    private String name;


    public Permission(UUID id, String name) {
        this.id = id;
        this.name = name;
    }

    public static Permission create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Permission name cannot be empty.");
        }
        return new Permission(UUID.randomUUID(), name.toUpperCase());
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}