package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.employee;

import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.EmployeeMapper;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.EmployeeMapperFactory;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.EmployeeModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class EmployeeRepositoryImpl implements IEmployeeRepository {

    private final SpringDataEmployeeRepository jpaRepository;
    private final EmployeeMapperFactory mapperFactory;
    private final EmployeeMapper employeeMapper;

    public EmployeeRepositoryImpl(SpringDataEmployeeRepository jpaRepository, EmployeeMapperFactory mapperFactory, EmployeeMapper employeeMapper) {
        this.jpaRepository = jpaRepository;
        this.mapperFactory = mapperFactory;
        this.employeeMapper = employeeMapper;
    }

    @Override
    public void save(Employee employee) {
        // 1. Pede à fábrica o mapper correto
        var mapper = mapperFactory.getMapperFor(employee);

        // 2. Usa o mapper específico para converter (sem saber qual é)
        EmployeeModel employeeModel = mapper.toModel(employee);

        // 3. Salva no banco
        this.jpaRepository.save(employeeModel);
    }

    @Override
    public Optional<Employee> findByUserId(UUID userId) {
        return this.jpaRepository.findByUser_Id(userId).map(employeeMapper::toEntity);
    }

    @Override
    public Optional<Employee> findById(UUID id) {
        return this.jpaRepository.findById(id).map(employeeMapper::toEntity);
    }
}