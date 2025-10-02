package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.address;

import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers.AddressMapper;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.AddressModel;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class AddressRepositoryImpl implements IAddressRepository {

    private final SpringDataAddressRepository jpaRepository;
    private final AddressMapper mapper;

    public AddressRepositoryImpl(SpringDataAddressRepository jpaRepository, AddressMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(Address address) {
        AddressModel addressModel = mapper.toModel(address);
        this.jpaRepository.save(addressModel);
    }

    @Override
    public Optional<Address> findById(UUID id) {
        return this.jpaRepository.findById(id).map(mapper::toEntity);
    }

}