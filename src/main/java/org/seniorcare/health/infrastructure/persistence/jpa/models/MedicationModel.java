package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "medications")
public class MedicationModel {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String commercialName;

    @Column(nullable = false)
    private String activeIngredient;

    @Column(nullable = false)
    private String pharmaceuticalForm;

    private String concentration;

    private String manufacturer;

    private String registrationNumber;

    @Column(nullable = false)
    private String therapeuticClass;

    @Column(nullable = false)
    private boolean controlledSubstance;

    public MedicationModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCommercialName() {
        return commercialName;
    }

    public void setCommercialName(String commercialName) {
        this.commercialName = commercialName;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public void setActiveIngredient(String activeIngredient) {
        this.activeIngredient = activeIngredient;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public void setPharmaceuticalForm(String pharmaceuticalForm) {
        this.pharmaceuticalForm = pharmaceuticalForm;
    }

    public String getConcentration() {
        return concentration;
    }

    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getTherapeuticClass() {
        return therapeuticClass;
    }

    public void setTherapeuticClass(String therapeuticClass) {
        this.therapeuticClass = therapeuticClass;
    }

    public boolean isControlledSubstance() {
        return controlledSubstance;
    }

    public void setControlledSubstance(boolean controlledSubstance) {
        this.controlledSubstance = controlledSubstance;
    }
}
