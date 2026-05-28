package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.manager;

import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.repositories.IManagerRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.ManagerMapper;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ManagerRepositoryImpl implements IManagerRepository {

    private final SpringDataManagerRepository jpaRepository;
    private final ManagerMapper managerMapper;

    public ManagerRepositoryImpl(SpringDataManagerRepository jpaRepository, ManagerMapper managerMapper) {
        this.jpaRepository = jpaRepository;
        this.managerMapper = managerMapper;
    }

    @Override
    public Optional<Manager> findById(UUID id) {
        return jpaRepository.findById(id).map(managerMapper::toEntity);
    }

    @Override
    public PageResult<Manager> findAll(Pagination pagination) {
        Page<Manager> page = jpaRepository
                .findAll(PageRequest.of(pagination.page(), pagination.size()))
                .map(managerMapper::toEntity);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pagination.page(), pagination.size());
    }
}
