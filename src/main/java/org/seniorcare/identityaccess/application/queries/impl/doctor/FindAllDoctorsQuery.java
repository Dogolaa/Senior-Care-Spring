package org.seniorcare.identityaccess.application.queries.impl.doctor;

import org.springframework.data.domain.Pageable;

public record FindAllDoctorsQuery(Pageable pageable) {
}
