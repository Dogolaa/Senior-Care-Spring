package org.seniorcare.identityaccess.application.queries.handlers.employee;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindAllEmployeesQuery;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.employee.EmployeeQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllEmployeesQueryHandler {

    private final EmployeeQueryRepository queryRepository;

    public FindAllEmployeesQueryHandler(EmployeeQueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDetailsDTO> handle(FindAllEmployeesQuery query) {
        return queryRepository.findAllDetails(query.pageable());
    }
}