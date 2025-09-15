package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface SpringDataUserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);
}