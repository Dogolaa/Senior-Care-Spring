package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.role;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataRoleRepository extends JpaRepository<RoleModel, UUID> {

    Optional<RoleModel> findByName(String name);
}