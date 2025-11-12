package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.repositories.resident;

import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;


@Repository
public interface SpringDataResidentRepository extends JpaRepository<ResidentModel, UUID> {
    Optional<ResidentModel> findByCpf(String cpf);

    @Query("SELECT r FROM ResidentModel r " +
            "LEFT JOIN FETCH r.familyLinks fl " +
            "LEFT JOIN FETCH r.allergies a " +
            "WHERE r.id = :id")
    Optional<ResidentModel> findByIdWithDetails(@Param("id") UUID id);
}