package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class RoleMapper {

    private final PermissionMapper permissionMapper;

    public RoleMapper(PermissionMapper permissionMapper) {
        this.permissionMapper = permissionMapper;
    }

    public Role toEntity(RoleModel model) {
        if (model == null) return null;

        var permissions = model.getPermissions().stream()
                .map(permissionMapper::toEntity)
                .collect(Collectors.toSet());

        return new Role(model.getId(), model.getName(), permissions);
    }

    public RoleModel toModel(Role entity) {
        if (entity == null) return null;

        var permissionModels = entity.getPermissions().stream()
                .map(permissionMapper::toModel)
                .collect(Collectors.toSet());

        RoleModel model = new RoleModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setPermissions(permissionModels);

        return model;
    }
}