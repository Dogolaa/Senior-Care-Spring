package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;

import java.util.Optional;
import java.util.UUID;

public interface IManagerRepository {

    Optional<Manager> findById(UUID id);

    PageResult<Manager> findAll(Pagination pagination);
}
