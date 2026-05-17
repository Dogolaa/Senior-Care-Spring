package org.seniorcare.residentmanagement.application.queries.impl.resident;

import org.springframework.data.domain.Pageable;

public record FindAllResidentsQuery(Pageable pageable) {
}
