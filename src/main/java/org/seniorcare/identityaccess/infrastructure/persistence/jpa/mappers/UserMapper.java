package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.vo.Email;
import org.seniorcare.identityaccess.domain.vo.HashedPassword;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.role.SpringDataRoleRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    private final SpringDataRoleRepository roleRepository;

    public UserMapper(SpringDataRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public UserModel toModel(User entity) {
        if (entity == null) return null;

        RoleModel roleModel = roleRepository.findById(entity.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + entity.getRoleId()));

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail().value());
        model.setPhone(entity.getPhone());
        model.setActive(entity.isActive());
        model.setAddressId(entity.getAddressId());
        model.setPassword(entity.getPassword().value());
        model.setRole(roleModel);
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public User toEntity(UserModel model) {
        if (model == null) return null;

        return new User(
                model.getId(),
                model.getName(),
                new Email(model.getEmail()),
                model.getPhone(),
                model.isActive(),
                model.getAddressId(),
                new HashedPassword(model.getPassword()),
                model.getRole() != null ? model.getRole().getId() : null,
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}