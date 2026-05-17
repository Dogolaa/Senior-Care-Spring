package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.domain.entities.Medication;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.seniorcare.health.infrastructure.persistence.jpa.mappers.MedicationPersistenceMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class MedicationRepositoryImpl implements IMedicationRepository {

    private final SpringDataMedicationRepository springDataRepository;
    private final MedicationPersistenceMapper mapper;

    public MedicationRepositoryImpl(SpringDataMedicationRepository springDataRepository, MedicationPersistenceMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Medication> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Medication> search(String name, int page, int count) {
        Pageable pageable = PageRequest.of(Math.max(0, page - 1), count);
        return springDataRepository
                .findByCommercialNameContainingIgnoreCaseOrActiveIngredientContainingIgnoreCase(name, name, pageable)
                .stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }
}
