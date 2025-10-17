package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.manager;

import org.seniorcare.identityaccess.domain.repositories.IManagerRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.ManagerMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ManagerRepositoryImpl implements IManagerRepository {

    private final SpringDataManagerRepository jpaRepository;
    private final ManagerMapper managerMapper;

    public ManagerRepositoryImpl(SpringDataManagerRepository jpaRepository, ManagerMapper managerMapper) {
        this.jpaRepository = jpaRepository;
        this.managerMapper = managerMapper;
    }

}