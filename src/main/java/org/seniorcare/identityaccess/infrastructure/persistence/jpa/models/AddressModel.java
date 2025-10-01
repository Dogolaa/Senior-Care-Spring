package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLRestriction;
import org.seniorcare.shared.infrastructure.persistence.Auditable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "address")
@EntityListeners(AuditingEntityListener.class)
@SQLRestriction("deleted_at IS NULL")
public class AddressModel extends Auditable {

    @Id
    private UUID id;

    @Column(name = "cep", nullable = false)
    private String cep;

    @Column(name = "country", nullable = false)
    private String country;

    @Column(name = "state", nullable = false)
    private String state;

    @Column(name = "city", nullable = false)
    private String city;

    @Column(name = "district", nullable = false)
    private String district;

    @Column(name = "street", nullable = false)
    private String street;

    @Column(name = "number", nullable = false)
    private Integer number;

    @Column(name = "complement", nullable = true)
    private String complement;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    public AddressModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AddressModel that)) return false;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getCep(), that.getCep()) && Objects.equals(getCountry(), that.getCountry()) && Objects.equals(getState(), that.getState()) && Objects.equals(getCity(), that.getCity()) && Objects.equals(getDistrict(), that.getDistrict()) && Objects.equals(getStreet(), that.getStreet()) && Objects.equals(getNumber(), that.getNumber()) && Objects.equals(getComplement(), that.getComplement()) && Objects.equals(getDeletedAt(), that.getDeletedAt());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCep(), getCountry(), getState(), getCity(), getDistrict(), getStreet(), getNumber(), getComplement(), getDeletedAt());
    }
}
