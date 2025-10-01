package org.seniorcare.identityaccess.application.dto.address;

import org.springframework.hateoas.RepresentationModel;

import java.util.UUID;

public class AddressDTO extends RepresentationModel<AddressDTO> {

    private final UUID id;
    private final String cep;
    private final String country;
    private final String state;
    private final String city;
    private final String district;
    private final String street;
    private final Integer number;
    private final String complement;

    public AddressDTO(UUID id, String cep, String country, String state, String city,
                      String district, String street, Integer number, String complement) {
        this.id = id;
        this.cep = cep;
        this.country = country;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
    }

    public UUID getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public String getCountry() {
        return country;
    }

    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }

    public String getDistrict() {
        return district;
    }

    public String getStreet() {
        return street;
    }

    public Integer getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }
}
