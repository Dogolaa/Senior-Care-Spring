package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.address;

import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataAddressRepository extends JpaRepository<AddressModel, UUID> {
}
