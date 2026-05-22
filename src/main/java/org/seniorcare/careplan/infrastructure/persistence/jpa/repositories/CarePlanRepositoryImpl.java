package org.seniorcare.careplan.infrastructure.persistence.jpa.repositories;

import org.seniorcare.careplan.domain.aggregates.CarePlan;
import org.seniorcare.careplan.domain.repositories.ICarePlanRepository;
import org.seniorcare.careplan.infrastructure.persistence.jpa.mappers.CarePlanMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class CarePlanRepositoryImpl implements ICarePlanRepository {

    private final SpringDataCarePlanRepository springDataRepository;
    private final CarePlanMapper mapper;

    public CarePlanRepositoryImpl(SpringDataCarePlanRepository springDataRepository, CarePlanMapper mapper) {
        this.springDataRepository = springDataRepository;
        this.mapper = mapper;
    }

    @Override
    public CarePlan save(CarePlan carePlan) {
        return mapper.toEntity(springDataRepository.save(mapper.toModel(carePlan)));
    }

    @Override
    public Optional<CarePlan> findById(UUID id) {
        return springDataRepository.findById(id).map(mapper::toEntity);
    }

    @Override
    public List<CarePlan> findByResidentId(UUID residentId) {
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
