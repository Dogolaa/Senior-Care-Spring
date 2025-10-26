package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.repositories.resident;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.mappers.ResidentMapper;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class ResidentRepositoryImpl implements IResidentsRepository {

    private final SpringDataResidentRepository jpaRepository;
    private final ResidentMapper residentMapper;

    public ResidentRepositoryImpl(SpringDataResidentRepository jpaRepository, ResidentMapper residentMapper) {
        this.jpaRepository = jpaRepository;
        this.residentMapper = residentMapper;
    }

    @Override
    public void save(Resident resident) {

        ResidentModel residentModel = residentMapper.toModel(resident);

        jpaRepository.save(residentModel);
    }

    @Override
    public Optional<Resident> findById(UUID id) {
        return this.jpaRepository.findById(id).map(residentMapper::toEntity);
    }

    @Override
    public Optional<Resident> findByCpf(Cpf cpf) {
        return this.jpaRepository.findByCpf(cpf.CPF()).map(residentMapper::toEntity);
    }
    
}