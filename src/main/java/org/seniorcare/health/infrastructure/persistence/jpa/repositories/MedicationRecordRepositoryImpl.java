package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.domain.entities.MedicationRecord;
import org.seniorcare.health.domain.repositories.IMedicationRecordRepository;
import org.seniorcare.health.infrastructure.persistence.jpa.mappers.MedicationRecordPersistenceMapper;
import org.seniorcare.health.infrastructure.persistence.jpa.models.MedicationRecordModel;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MedicationRecordRepositoryImpl implements IMedicationRecordRepository {

    private final SpringDataMedicationRecordRepository springDataRepository;
    private final MedicationRecordPersistenceMapper mapper;

    public MedicationRecordRepositoryImpl(SpringDataMedicationRecordRepository springDataRepository, MedicationRecordPersistenceMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public MedicationRecord save(MedicationRecord medicationRecord) {
        MedicationRecordModel model = mapper.toModel(medicationRecord);
        MedicationRecordModel savedModel = springDataRepository.save(model);
        return mapper.toDomain(savedModel);
    }

    @Override
    public Optional<MedicationRecord> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<MedicationRecord> findByResidentId(UUID residentId) {
        return springDataRepository.findByResidentId(residentId)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
