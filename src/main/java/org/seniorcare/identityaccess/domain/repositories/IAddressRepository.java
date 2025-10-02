package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Address;

import java.util.Optional;
import java.util.UUID;

public interface IAddressRepository {

    void save(Address address);


    Optional<Address> findById(UUID id);


}
