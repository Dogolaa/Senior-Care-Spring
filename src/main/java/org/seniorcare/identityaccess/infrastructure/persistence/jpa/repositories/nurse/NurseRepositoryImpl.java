package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.nurse;

import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.NurseMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
        return jpaRepository.findByCoren(coren)
                .map(nurseMapper::toEntity);
    }

    @Override
    public Optional<Nurse> findById(UUID id) {
        return this.jpaRepository.findById(id).map(nurseMapper::toEntity);
    }


    @Override
    public Page<Nurse> findAll(Pageable pageable) {
        return this.jpaRepository.findAll(pageable).map(nurseMapper::toEntity);
    }
}