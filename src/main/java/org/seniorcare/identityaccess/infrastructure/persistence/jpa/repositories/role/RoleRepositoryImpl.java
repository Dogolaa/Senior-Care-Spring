package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.role;

import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.RoleMapper;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements IRoleRepository {

    private Logger logger = LoggerFactory.getLogger(RoleRepositoryImpl.class.getName());

    private final SpringDataRoleRepository jpaRepository;
    private final RoleMapper roleMapper;

    public RoleRepositoryImpl(SpringDataRoleRepository jpaRepository, RoleMapper roleMapper) {
        this.jpaRepository = jpaRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    @Cacheable(value = "roles_by_name", key = "#name")
    public Optional<Role> findByName(String name) {
        logger.info("### BUSCANDO ROLE '{}' NO BANCO DE DADOS... ###", name);
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

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll().stream()
                .map(roleModel -> roleMapper.toEntity(roleModel))
                .collect(Collectors.toList());
    }
}