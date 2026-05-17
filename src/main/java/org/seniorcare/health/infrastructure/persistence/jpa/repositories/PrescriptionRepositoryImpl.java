package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.domain.entities.Prescription;
import org.seniorcare.health.domain.repositories.IPrescriptionRepository;
import org.seniorcare.health.infrastructure.persistence.jpa.mappers.PrescriptionPersistenceMapper;
import org.seniorcare.health.infrastructure.persistence.jpa.models.PrescriptionModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PrescriptionRepositoryImpl implements IPrescriptionRepository {

    private final SpringDataPrescriptionRepository springDataRepository;
    private final PrescriptionPersistenceMapper mapper;

    public PrescriptionRepositoryImpl(SpringDataPrescriptionRepository springDataRepository, PrescriptionPersistenceMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Prescription save(Prescription prescription) {
        PrescriptionModel model = mapper.toModel(prescription);
        PrescriptionModel savedModel = springDataRepository.save(model);
        return mapper.toDomain(savedModel);
    }

    @Override
    public Optional<Prescription> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Prescription> findByHealthRecordId(UUID healthRecordId) {
        return springDataRepository.findByMedicalRecordId(healthRecordId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
