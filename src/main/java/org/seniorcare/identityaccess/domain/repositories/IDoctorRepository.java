package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IDoctorRepository {
    Optional<Doctor> findByCrm(String crm);

    Optional<Doctor> findById(UUID id);

    Page<Doctor> findAll(Pageable pageable);
}