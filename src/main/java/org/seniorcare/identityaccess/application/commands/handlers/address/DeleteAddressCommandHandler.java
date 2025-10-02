package org.seniorcare.identityaccess.application.commands.handlers.address;

import org.seniorcare.identityaccess.application.commands.impl.address.DeleteAddressCommand;
import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteAddressCommandHandler {

    private final IAddressRepository addressRepository;

    public DeleteAddressCommandHandler(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public void handle(DeleteAddressCommand command) {

        Address addressToDelete = addressRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("Address with id " + command.id() + " not found."));

        addressToDelete.softDelete();

        addressRepository.save(addressToDelete);
    }

}
