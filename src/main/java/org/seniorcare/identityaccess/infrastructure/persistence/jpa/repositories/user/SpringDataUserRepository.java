package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.user;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface SpringDataUserRepository extends JpaRepository<UserModel, UUID> {
    Optional<UserModel> findByEmail(String email);

    @Query("SELECT u FROM UserModel u ORDER BY u.isActive DESC, u.name ASC")
    Page<UserModel> findAllOrderedByActiveFirst(Pageable pageable);
}