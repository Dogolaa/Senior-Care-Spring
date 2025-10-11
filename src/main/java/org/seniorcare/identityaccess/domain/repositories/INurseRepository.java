package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Nurse;

import java.util.Optional;

public interface INurseRepository {
    Optional<Nurse> findByCoren(String coren);
}