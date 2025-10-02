package org.seniorcare.identityaccess.application.commands.handlers.address;

import org.seniorcare.identityaccess.application.commands.impl.address.UpdateAddressCommand;
import org.seniorcare.identityaccess.application.dto.address.AddressDTO;
import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateAddressCommandHandler {

    private final IAddressRepository addressRepository;

    public UpdateAddressCommandHandler(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public AddressDTO handle(UpdateAddressCommand command) {

        Address addressToUpdate = addressRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + command.id() + " not found."));

        addressToUpdate.update(
                command.cep(),
                command.country(),
                command.state(),
                command.city(),
                command.district(),
                command.street(),
                command.number(),
                command.complement()
        );

        addressRepository.save(addressToUpdate);

        return toDTO(addressToUpdate);
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