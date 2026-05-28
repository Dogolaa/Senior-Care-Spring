package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.doctor;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.DoctorMapper;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class DoctorRepositoryImpl implements IDoctorRepository {

    private final SpringDataDoctorRepository jpaRepository;
    private final DoctorMapper doctorMapper;

    public DoctorRepositoryImpl(SpringDataDoctorRepository jpaRepository, DoctorMapper doctorMapper) {
        this.jpaRepository = jpaRepository;
        this.doctorMapper = doctorMapper;
    }

    @Override
    public Optional<Doctor> findByCrm(String crm) {
        return jpaRepository.findByCrm(crm).map(doctorMapper::toEntity);
    }

    @Override
    public Optional<Doctor> findById(UUID id) {
        return jpaRepository.findById(id).map(doctorMapper::toEntity);
    }

    @Override
    public PageResult<Doctor> findAll(Pagination pagination) {
        Page<Doctor> page = jpaRepository
                .findAll(PageRequest.of(pagination.page(), pagination.size()))
                .map(doctorMapper::toEntity);
        return new PageResult<>(page.getContent(), page.getTotalElements(), pagination.page(), pagination.size());
    }
}
