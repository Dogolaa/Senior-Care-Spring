package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.manager;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.ManagerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataManagerRepository extends JpaRepository<ManagerModel, UUID> {
}