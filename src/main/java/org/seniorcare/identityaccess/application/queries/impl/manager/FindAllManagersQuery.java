package org.seniorcare.identityaccess.application.queries.impl.manager;

import org.springframework.data.domain.Pageable;

public record FindAllManagersQuery(Pageable pageable) {
}
