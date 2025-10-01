package org.seniorcare.identityaccess.application.dto.address;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class AddressCreateDTO extends RepresentationModel<AddressCreateDTO> {

    private final UUID id;

    public AddressCreateDTO(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

}
