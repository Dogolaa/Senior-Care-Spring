package org.seniorcare.identityaccess.application.queries.handlers.employee;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindEmployeeByIdQuery;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.employee.EmployeeQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindEmployeeByIdQueryHandler {

    private final EmployeeQueryRepository queryRepository;

    public FindEmployeeByIdQueryHandler(EmployeeQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDetailsDTO> handle(FindEmployeeByIdQuery query) {
        return queryRepository.findDetailsById(query.id());
    }
}