package org.seniorcare.identityaccess.domain.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Role {

    private final UUID id;
    private String name;
    private Set<Permission> permissions;

    private Role(UUID id, String name) {
        this.id = id;
        this.name = name;
        this.permissions = new HashSet<>();
    }

    public static Role create(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Role name cannot be empty.");
        }
        return new Role(UUID.randomUUID(), name.toUpperCase());
    }

    public Role(UUID id, String name, Set<Permission> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public void addPermission(Permission permission) {
        this.permissions.add(permission);
    }

    public void removePermission(Permission permission) {
        this.permissions.remove(permission);
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }
}