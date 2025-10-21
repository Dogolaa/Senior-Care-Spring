package org.seniorcare.identityaccess.application.queries.impl.employee;

import org.springframework.data.domain.Pageable;

public record FindAllEmployeesQuery(Pageable pageable) {
}