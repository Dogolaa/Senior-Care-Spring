package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;


import jakarta.persistence.*;
import org.seniorcare.shared.infrastructure.persistence.Auditable;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleModel extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private Set<PermissionModel> permissions = new HashSet<>();

    public RoleModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<PermissionModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<PermissionModel> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoleModel roleModel)) return false;
        return Objects.equals(getId(), roleModel.getId()) && Objects.equals(getName(), roleModel.getName()) && Objects.equals(getPermissions(), roleModel.getPermissions());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getPermissions());
    }
}
