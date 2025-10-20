package org.seniorcare.identityaccess.application.queries.impl.nurse;

import org.springframework.data.domain.Pageable;

public record FindAllNursesQuery(Pageable pageable) {
}
