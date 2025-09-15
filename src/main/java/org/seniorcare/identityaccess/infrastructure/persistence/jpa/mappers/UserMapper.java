package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;


public class UserMapper {

    public static UserModel toModel(User entity) {
        if (entity == null) return null;

        UserModel model = new UserModel();
        model.setId(entity.getId());
        model.setName(entity.getName());
        model.setEmail(entity.getEmail());
        model.setPhone(entity.getPhone());
        model.setActive(entity.isActive());
        model.setAddressId(entity.getAddressId());
        model.setPassword(entity.getPassword());
        model.setRoleId(entity.getRoleId());
        return model;
    }

    public static User toEntity(UserModel model) {
        if (model == null) return null;
        
        try {
            var constructor = User.class.getDeclaredConstructor(
                    java.util.UUID.class, String.class, String.class, String.class,
                    boolean.class, java.util.UUID.class, String.class, java.util.UUID.class
            );
            constructor.setAccessible(true);

            return null;
        } catch (Exception e) {
            throw new RuntimeException("Could not map model to entity", e);
        }
    }
}