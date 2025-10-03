package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Role;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IRoleRepository {

    void save(Role role);

    Optional<Role> findById(UUID id);

    Optional<Role> findByName(String name);

    List<Role> findAll();
}