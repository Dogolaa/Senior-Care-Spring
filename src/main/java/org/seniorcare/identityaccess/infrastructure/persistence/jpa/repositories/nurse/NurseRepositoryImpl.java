package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.nurse;

import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.NurseMapper;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class NurseRepositoryImpl implements INurseRepository {

    private final SpringDataNurseRepository jpaRepository;
    private final NurseMapper nurseMapper;

    public NurseRepositoryImpl(SpringDataNurseRepository jpaRepository, NurseMapper nurseMapper) {
        this.jpaRepository = jpaRepository;
        this.nurseMapper = nurseMapper;
    }

    @Override
    public Optional<Nurse> findByCoren(String coren) {
        return jpaRepository.findByCoren(coren).map(nurseMapper::toEntity);
    }

    @Override
    public Optional<Nurse> findById(UUID id) {
        return jpaRepository.findById(id).map(nurseMapper::toEntity);
    }

    @Override
    public PageResult<Nurse> findAll(Pagination pagination) {
        Page<Nurse> page = jpaRepository
                .findAll(PageRequest.of(pagination.page(), pagination.size()))
                .map(nurseMapper::toEntity);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pagination.page(), pagination.size());
    }
}
