package org.seniorcare.identityaccess.application.dto.address;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class AddressDTO extends RepresentationModel<AddressDTO> {

    private final UUID id;

    public AddressDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
