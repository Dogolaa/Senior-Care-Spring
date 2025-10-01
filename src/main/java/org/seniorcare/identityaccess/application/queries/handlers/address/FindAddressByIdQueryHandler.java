package org.seniorcare.identityaccess.application.queries.handlers.address;

import org.seniorcare.identityaccess.application.dto.address.AddressDTO;
import org.seniorcare.identityaccess.application.queries.impl.address.FindAddressByIdQuery;
import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindAddressByIdQueryHandler {

    private final IAddressRepository addressRepository;

    public FindAddressByIdQueryHandler(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional(readOnly = true)
    public Optional<AddressDTO> handle(FindAddressByIdQuery query) {
        return addressRepository.findById(query.id())
                .map(this::toDTO);
    }

    private AddressDTO toDTO(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getCep(),
                address.getCountry(),
                address.getState(),
                address.getCity(),
                address.getDistrict(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement()

        );
    }
}