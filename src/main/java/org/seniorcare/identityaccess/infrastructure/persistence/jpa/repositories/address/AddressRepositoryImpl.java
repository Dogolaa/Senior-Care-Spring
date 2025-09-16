package org.seniorcare.identityaccess.infrastructure.persistence.jpa.repositories.address;

import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.AddressModel;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@Primary
public class AddressRepositoryImpl implements IAddressRepository {

    private final SpringDataAddressRepository jpaRepository;

    public AddressRepositoryImpl(SpringDataAddressRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Address address) {
        AddressModel addressModel = toModel(address);
        this.jpaRepository.save(addressModel);
    }

    @Override
    public Optional<Address> findById(UUID id) {
        return Optional.empty();
    }

    @Override
    public Page<Address> findAll(Pageable pageable) {
        return null;
    }


    private AddressModel toModel(Address entity) {
        if (entity == null) return null;

        AddressModel model = new AddressModel();
        model.setId(entity.getId());
        model.setCep(entity.getCep());
        model.setCountry(entity.getCountry());
        model.setState(entity.getState());
        model.setCity(entity.getCity());
        model.setDistrict(entity.getDistrict());
        model.setStreet(entity.getStreet());
        model.setNumber(entity.getNumber());
        model.setComplement(entity.getComplement());

        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

}
