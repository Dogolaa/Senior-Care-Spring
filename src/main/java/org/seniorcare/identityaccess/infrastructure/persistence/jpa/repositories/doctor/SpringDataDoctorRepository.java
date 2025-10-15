package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.doctor;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.DoctorModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataDoctorRepository extends JpaRepository<DoctorModel, UUID> {
    Optional<DoctorModel> findByCrm(String crm);
}