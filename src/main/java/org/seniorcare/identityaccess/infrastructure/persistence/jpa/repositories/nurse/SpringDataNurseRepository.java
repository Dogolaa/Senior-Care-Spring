package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.nurse;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.NurseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataNurseRepository extends JpaRepository<NurseModel, UUID> {
    Optional<NurseModel> findByCoren(String coren);
}