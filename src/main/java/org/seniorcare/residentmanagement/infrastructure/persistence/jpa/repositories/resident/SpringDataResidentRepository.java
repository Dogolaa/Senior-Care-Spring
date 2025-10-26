package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.repositories.resident;

import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface SpringDataResidentRepository extends JpaRepository<ResidentModel, UUID> {
    Optional<ResidentModel> findByCpf(String cpf);
}