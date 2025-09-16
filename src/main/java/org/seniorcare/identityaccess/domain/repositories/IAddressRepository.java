package org.seniorcare.identityaccess.domain.repositories;

import org.seniorcare.identityaccess.domain.entities.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface IAddressRepository {

    void save(Address address);


    Optional<Address> findById(UUID id);
    

    Page<Address> findAll(Pageable pageable);

}
