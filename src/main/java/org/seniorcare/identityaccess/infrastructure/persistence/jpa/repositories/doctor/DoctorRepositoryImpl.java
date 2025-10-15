package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.doctor;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.DoctorMapper;
import org.springframework.stereotype.Repository;

import java.util.Optional;

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
        return jpaRepository.findByCrm(crm)
                .map(doctorMapper::toEntity);
    }
}