package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Manager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IManagerRepository {

    Optional<Manager> findById(UUID id);

    Page<Manager> findAll(Pageable pageable);

}