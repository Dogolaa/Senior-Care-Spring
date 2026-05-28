package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface INurseRepository {

    Optional<Nurse> findByCoren(String coren);

    Optional<Nurse> findById(UUID id);

    PageResult<Nurse> findAll(Pagination pagination);
}
