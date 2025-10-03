package org.seniorcare.identityaccess.application.dto.role;

import java.util.Set;
import java.util.UUID;

public class RoleDTO {

    private final UUID id;
    private final String name;
    private final Set<String> permissions;

    public RoleDTO(UUID id, String name, Set<String> permissions) {
        this.id = id;
        this.name = name;
        this.permissions = permissions;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<String> getPermissions() {
        return permissions;
    }
}
