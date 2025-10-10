package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.employee;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringDataEmployeeRepository extends JpaRepository<EmployeeModel, UUID> {
    Optional<EmployeeModel> findByUser_Id(UUID userId);
}
