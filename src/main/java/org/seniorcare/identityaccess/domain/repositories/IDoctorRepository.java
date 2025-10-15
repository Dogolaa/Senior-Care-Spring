package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Doctor;

import java.util.Optional;

public interface IDoctorRepository {
    Optional<Doctor> findByCrm(String crm);
}