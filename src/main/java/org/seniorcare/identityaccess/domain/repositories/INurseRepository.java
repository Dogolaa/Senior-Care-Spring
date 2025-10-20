package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface INurseRepository {
    Optional<Nurse> findByCoren(String coren);

    Optional<Nurse> findById(UUID id);

    Page<Nurse> findAll(Pageable pageable);
}