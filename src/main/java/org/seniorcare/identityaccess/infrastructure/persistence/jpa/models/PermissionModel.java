package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.seniorcare.shared.infrastructure.persistence.Auditable;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "permissions")
public class PermissionModel extends Auditable {

    @Id
    private UUID id;

    @Column(nullable = false, unique = true)
    private String permissionName;

    public PermissionModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PermissionModel that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getPermissionName(), that.getPermissionName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPermissionName());
    }
}
