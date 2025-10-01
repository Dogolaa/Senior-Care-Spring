package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserMapper() {
    }

    public UserModel toModel(User entity, RoleModel roleModel) {
        if (entity == null) return null;

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setActive(entity.isActive());
        model.setAddressId(entity.getAddressId());
        model.setPassword(entity.getPassword());
        model.setRole(roleModel);
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public User toEntity(UserModel model) {
        if (model == null) return null;

        return new User(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.isActive(),
                model.getAddressId(),
                model.getPassword(),
                model.getRole() != null ? model.getRole().getId() : null,
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}