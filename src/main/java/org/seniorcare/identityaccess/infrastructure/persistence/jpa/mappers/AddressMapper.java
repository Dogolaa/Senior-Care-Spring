package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Address;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.AddressModel;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressMapper() {
    }

    public AddressModel toModel(Address entity) {
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
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public Address toEntity(AddressModel model) {
        if (model == null) return null;

        return new Address(
                model.getId(),
                model.getCep(),
                model.getCountry(),
                model.getState(),
                model.getCity(),
                model.getDistrict(),
                model.getStreet(),
                model.getNumber(),
                model.getComplement(),
                model.getCreatedAt(),
                model.getUpdatedAt(),
                model.getDeletedAt()
        );
    }
}