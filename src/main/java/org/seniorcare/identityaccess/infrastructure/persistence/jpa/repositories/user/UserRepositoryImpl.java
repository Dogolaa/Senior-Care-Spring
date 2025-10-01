package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.user;

import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.UserMapper;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.role.SpringDataRoleRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class UserRepositoryImpl implements IUserRepository {

    private final SpringDataUserRepository jpaRepository;
    private final SpringDataRoleRepository roleRepository;
    private final UserMapper userMapper;

    public UserRepositoryImpl(SpringDataUserRepository jpaRepository, SpringDataRoleRepository roleRepository, UserMapper userMapper) {
        this.jpaRepository = jpaRepository;
        this.roleRepository = roleRepository;
        this.userMapper = userMapper;
    }

    @Override
    public void save(User user) {
        RoleModel roleModel = roleRepository.findById(user.getRoleId())
                .orElseThrow(() -> new NoSuchElementException("Role not found for ID: " + user.getRoleId()));

        UserModel userModel = userMapper.toModel(user, roleModel);

        jpaRepository.save(userModel);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return this.jpaRepository.findById(id).map(userMapper::toEntity);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return this.jpaRepository.findByEmail(email).map(userMapper::toEntity);
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return this.jpaRepository.findAll(pageable).map(userMapper::toEntity);
    }
}