package org.seniorcare.identityaccess.application.queries.handlers.employee;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.ports.IEmployeeQueryPort;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindEmployeeByIdQuery;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindEmployeeByIdQueryHandler {

    private final IEmployeeQueryPort employeeQueryPort;

    public FindEmployeeByIdQueryHandler(IEmployeeQueryPort employeeQueryPort) {
        this.employeeQueryPort = employeeQueryPort;
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeDetailsDTO> handle(FindEmployeeByIdQuery query) {
        return employeeQueryPort.findDetailsById(query.id());
    }
}
