package org.seniorcare.identityaccess.application.commands.impl.address;

import java.util.UUID;

public record UpdateAddressCommand(
        UUID id,
        String cep,
        String country,
        String state,
        String city,
        String district,
        String street,
        Integer number,
        String complement) {
}

