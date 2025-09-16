package org.seniorcare.identityaccess.application.commands.impl.address;

public record CreateAddressCommand(
        String cep,
        String country,
        String state,
        String city,
        String district,
        String street,
        Integer number,
        String complement
) {
}
