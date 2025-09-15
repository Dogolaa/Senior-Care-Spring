package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;


@Repository
@Primary
public class UserRepositoryImpl implements IUserRepository {

    private final SpringDataUserRepository jpaRepository;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(User user) {
        UserModel userModel = this.toModel(user);
        this.jpaRepository.save(userModel);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.jpaRepository.findById(id).map(this::toEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaRepository.findByEmail(email).map(this::toEntity);
    }

    @Override
    public void deleteById(UUID id) {
        this.jpaRepository.deleteById(id);
    }


    private User toEntity(UserModel model) {
        if (model == null) return null;


        try {
            User user = User.create(
                    model.getName(), model.getEmail(), model.getPhone(),
                    model.getAddressId(), "placeholder123", model.getRoleId()
            );

            Field idField = User.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(user, model.getId());

            Field passwordField = User.class.getDeclaredField("password");
            passwordField.setAccessible(true);
            passwordField.set(user, model.getPassword());

            Field isActiveField = User.class.getDeclaredField("isActive");
            isActiveField.setAccessible(true);
            isActiveField.set(user, model.isActive());

            return user;
        } catch (Exception e) {
            throw new RuntimeException("Error mapping UserModel to User entity", e);
        }
    }

    private UserModel toModel(User entity) {
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
}