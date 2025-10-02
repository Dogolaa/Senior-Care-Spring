package org.seniorcare.identityaccess.api.rest.dto.address;

public record UpdateAddressRequest(
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
