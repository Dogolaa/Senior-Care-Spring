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
        var mapper = mapperFactory.getMapper(employee);
        @SuppressWarnings("unchecked")
        EmployeeModel employeeModel = mapper.toModel(employee);


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