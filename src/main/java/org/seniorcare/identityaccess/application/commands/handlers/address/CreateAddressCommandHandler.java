package org.seniorcare.identityaccess.application.commands.handlers.address;

import org.seniorcare.identityaccess.application.commands.impl.address.CreateAddressCommand;
import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.domain.repositories.IAddressRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateAddressCommandHandler {

    private final IAddressRepository addressRepository;

    public CreateAddressCommandHandler(IAddressRepository addressRepository) {
        this.addressRepository = addressRepository;
    }

    @Transactional
    public UUID handle(CreateAddressCommand command) {

        Address newAddress = Address.create(
                command.cep(),
                command.country(),
                command.state(),
                command.city(),
                command.district(),
                command.street(),
                command.number(),
                command.complement()
        );
        addressRepository.save(newAddress);
        return newAddress.getId();
    }

}
