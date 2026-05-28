package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface IDoctorRepository {

    Optional<Doctor> findByCrm(String crm);

    Optional<Doctor> findById(UUID id);

    PageResult<Doctor> findAll(Pagination pagination);
}
