package org.seniorcare.residentmanagement.infrastructure.persistence.jpa.repositories.resident;

import org.seniorcare.residentmanagement.domain.aggregates.Resident;
import org.seniorcare.residentmanagement.domain.repositories.IResidentsRepository;
import org.seniorcare.residentmanagement.domain.vo.Cpf;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.mappers.ResidentMapper;
import org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models.ResidentModel;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.List;
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
        if (residentModel.getFamilyLinks() != null) {
            residentModel.getFamilyLinks().forEach(link -> link.setResident(residentModel));
        }
        jpaRepository.save(residentModel);
    }

    @Override
    public Optional<Resident> findById(UUID id) {
        return jpaRepository.findByIdWithDetails(id).map(residentMapper::toEntity);
    }

    @Override
    public Optional<Resident> findByCpf(Cpf cpf) {
        return jpaRepository.findByCpf(cpf.CPF()).map(residentMapper::toEntity);
    }

    @Override
    public boolean existsByCpf(Cpf cpf) {
        return jpaRepository.findByCpf(cpf.CPF()).isPresent();
    }

    @Override
    public PageResult<Resident> findAll(Pagination pagination) {
        Page<Resident> page = jpaRepository
                .findAll(PageRequest.of(pagination.page(), pagination.size()))
                .map(residentMapper::toEntity);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pagination.page(), pagination.size());
    }

    @Override
    public List<Resident> findByFamilyMemberId(UUID userId) {
        return jpaRepository.findByFamilyMemberId(userId).stream()
                .map(residentMapper::toEntity)
                .toList();
    }
}
