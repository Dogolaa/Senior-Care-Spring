package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.role;

import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.RoleMapper;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {

    private final SpringDataRoleRepository jpaRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryImpl(SpringDataRoleRepository jpaRepository, RoleMapper roleMapper) {
        this.jpaRepository = jpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public Optional<Role> findByName(String name) {
        return jpaRepository.findByName(name).map(roleModel -> roleMapper.toEntity(roleModel));
    }

    @Override
    public void save(Role role) {
        RoleModel roleModel = roleMapper.toModel(role);
        jpaRepository.save(roleModel);
    }

    @Override
    public Optional<Role> findById(UUID id) {
        return jpaRepository.findById(id).map(roleModel -> roleMapper.toEntity(roleModel));
    }
}