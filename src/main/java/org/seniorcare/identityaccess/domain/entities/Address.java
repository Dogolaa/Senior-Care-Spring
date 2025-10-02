package org.seniorcare.identityaccess.domain.entities;

import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;

import java.time.Instant;
import java.util.UUID;

public class Address {

    private final UUID id;
    private String cep;
    private String country;
    private String state;
    private String city;
    private String district;
    private String street;
    private Integer number;
    private String complement;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Address(UUID id, String cep, String country, String state, String city, String district, String street, Integer number, String complement) {
        this.id = id;
        this.cep = cep;
        this.country = country;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
        this.deletedAt = null;
    }

    public Address(UUID id, String cep, String country, String state, String city, String district, String street, Integer number, String complement, Instant createdAt, Instant updatedAt, Instant deletedAt) {
        this.id = id;
        this.cep = cep;
        this.country = country;
        this.state = state;
        this.city = city;
        this.district = district;
        this.street = street;
        this.number = number;
        this.complement = complement;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public UUID getId() {
        return id;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getComplement() {
        return complement;
    }

    public void setComplement(String complement) {
        this.complement = complement;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }


    public static Address create(String cep, String country, String state, String city, String district, String street, Integer number, String complement) {
        if (cep == null || cep.trim().isEmpty()) {
            throw new BadRequestException("Address CEP cannot be empty.");
        }
        if (country == null || country.trim().isEmpty()) {
            throw new BadRequestException("Address Country cannot be empty.");
        }
        if (state == null || state.trim().isEmpty()) {
            throw new BadRequestException("Address State cannot be empty.");
        }
        if (city == null || city.trim().isEmpty()) {
            throw new BadRequestException("Address City cannot be empty.");
        }
        if (district == null || district.trim().isEmpty()) {
            throw new BadRequestException("Address District cannot be empty.");
        }
        if (street == null || street.trim().isEmpty()) {
            throw new BadRequestException("Address Street cannot be empty.");
        }
        if (number == null) {
            throw new BadRequestException("Address Number cannot be empty.");
        }

        return new Address(UUID.randomUUID(), cep, country, state, city, district, street, number, complement);
    }

    public void update(String newCep, String newCountry, String newState, String newCity,
                       String newDistrict, String newStreet, Integer newNumber, String newComplement) {

        this.cep = newCep;
        this.country = newCountry;
        this.state = newState;
        this.city = newCity;
        this.district = newDistrict;
        this.street = newStreet;
        this.number = newNumber;
        this.complement = newComplement;
        this.updatedAt = Instant.now();
    }

    public void softDelete() {
        if (this.deletedAt != null) {
            throw new ResourceNotFoundException("Address is already deleted.");
        }
        this.deletedAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
