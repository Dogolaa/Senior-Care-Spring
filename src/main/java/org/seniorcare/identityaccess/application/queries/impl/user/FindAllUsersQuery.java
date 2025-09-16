package org.seniorcare.identityaccess.application.queries.impl.user;

import org.springframework.data.domain.Pageable;

public record FindAllUsersQuery(Pageable pageable) {
}
