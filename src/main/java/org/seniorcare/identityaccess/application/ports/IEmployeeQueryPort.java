package org.seniorcare.identityaccess.application.ports;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IEmployeeQueryPort {

    Optional<EmployeeDetailsDTO> findDetailsById(UUID id);

    Page<EmployeeDetailsDTO> findAllDetails(Pageable pageable);
}
