package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

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
        // Para Soft Delete, este método não deve ser usado diretamente.
        // A lógica será: buscar o usuário, chamar user.softDelete(), e depois userRepo.save(user).
        this.jpaRepository.deleteById(id);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.jpaRepository.findAll(pageable).map(this::toEntity);
    }

    private User toEntity(UserModel model) {
        if (model == null) return null;


        return new User(
                model.getId(),
                model.getName(),
                model.getEmail(),
                model.getPhone(),
                model.isActive(),
                model.getAddressId(),
                model.getPassword(),
                model.getRoleId(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
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

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }
}