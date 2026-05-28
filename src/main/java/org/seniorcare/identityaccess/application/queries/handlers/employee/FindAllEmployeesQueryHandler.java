package org.seniorcare.identityaccess.application.queries.handlers.employee;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.ports.IEmployeeQueryPort;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindAllEmployeesQuery;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllEmployeesQueryHandler {

    private final IEmployeeQueryPort employeeQueryPort;

    public FindAllEmployeesQueryHandler(IEmployeeQueryPort employeeQueryPort) {
        this.employeeQueryPort = employeeQueryPort;
    }

    @Transactional(readOnly = true)
    public Page<EmployeeDetailsDTO> handle(FindAllEmployeesQuery query) {
        return employeeQueryPort.findAllDetails(query.pageable());
    }
}
