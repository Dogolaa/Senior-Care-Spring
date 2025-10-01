package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Permission;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.PermissionModel;
import org.springframework.stereotype.Component;

@Component
public class PermissionMapper {

    public Permission toEntity(PermissionModel model) {
        if (model == null) return null;
        return new Permission(model.getId(), model.getPermissionName());
    }

    public PermissionModel toModel(Permission entity) {
        if (entity == null) return null;
        PermissionModel model = new PermissionModel();
        model.setId(entity.getId());
        model.setPermissionName(entity.getName());
        return model;
    }
}