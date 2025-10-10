package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Employee;

import java.util.Optional;
import java.util.UUID;

public interface IEmployeeRepository {

    void save(Employee employee);

    Optional<Employee> findByUserId(UUID userId);

    Optional<Employee> findById(UUID employeeId);

}
