package org.seniorcare.shared.infrastructure.persistence;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = {
        "org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories",
        "org.seniorcare.health.infrastructure.persistence.jpa.repositories",
        "org.seniorcare.residentmanagement.infrastructure.persistence.jpa.repositories",
        "org.seniorcare.communication.infrastructure.persistence.jpa.repositories"
})
@EntityScan(basePackages = {
        "org.seniorcare.identityaccess.infrastructure.persistence.jpa.models",
        "org.seniorcare.health.infrastructure.persistence.jpa.models",
        "org.seniorcare.residentmanagement.infrastructure.persistence.jpa.models",
        "org.seniorcare.communication.infrastructure.persistence.jpa.models"
})
public class SharedPersistenceConfiguration {
}
