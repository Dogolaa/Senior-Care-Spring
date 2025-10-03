package org.seniorcare.identityaccess.application.queries.handlers.role;

import org.seniorcare.identityaccess.application.dto.role.RoleDTO;
import org.seniorcare.identityaccess.application.queries.impl.role.FindAllRolesQuery;
import org.seniorcare.identityaccess.domain.entities.Permission;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindAllRolesQueryHandler {

    private final IRoleRepository roleRepository;

    public FindAllRolesQueryHandler(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<RoleDTO> handle(FindAllRolesQuery query) {
        return roleRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private RoleDTO toDTO(Role role) {
        var permissionNames = role.getPermissions().stream()
                .map(Permission::getName)
                .collect(Collectors.toSet());

        return new RoleDTO(role.getId(), role.getName(), permissionNames);
    }
}