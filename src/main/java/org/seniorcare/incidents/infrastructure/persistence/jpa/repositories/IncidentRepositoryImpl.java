package org.seniorcare.incidents.infrastructure.persistence.jpa.repositories;

import org.seniorcare.incidents.domain.aggregates.Incident;
import org.seniorcare.incidents.domain.repositories.IIncidentRepository;
import org.seniorcare.incidents.infrastructure.persistence.jpa.mappers.IncidentMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class IncidentRepositoryImpl implements IIncidentRepository {

    private final SpringDataIncidentRepository springDataRepository;
    private final IncidentMapper mapper;

    public IncidentRepositoryImpl(SpringDataIncidentRepository springDataRepository, IncidentMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public Incident save(Incident incident) {
        return mapper.toEntity(springDataRepository.save(mapper.toModel(incident)));
    }

    @Override
    public Optional<Incident> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toEntity);
    }

    @Override
    public List<Incident> findByResidentId(UUID residentId) {
        return springDataRepository.findByResidentId(residentId)
                .stream()
                .map(mapper::toEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        springDataRepository.deleteById(id);
    }
}
