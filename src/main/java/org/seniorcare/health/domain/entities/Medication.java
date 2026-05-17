package org.seniorcare.health.domain.entities;

import java.util.UUID;

public class Medication {

    private UUID id;
    private String commercialName;
    private String activeIngredient;
    private String pharmaceuticalForm;
    private String concentration;
    private String manufacturer;
    private String registrationNumber;
    private String therapeuticClass;
    private boolean controlledSubstance;

    public Medication() {
    }

    public Medication(UUID id, String commercialName, String activeIngredient, String pharmaceuticalForm,
                      String concentration, String manufacturer, String registrationNumber,
                      String therapeuticClass, boolean controlledSubstance) {
        this.id = id;
        this.commercialName = commercialName;
        this.activeIngredient = activeIngredient;
        this.pharmaceuticalForm = pharmaceuticalForm;
        this.concentration = concentration;
        this.manufacturer = manufacturer;
        this.registrationNumber = registrationNumber;
        this.therapeuticClass = therapeuticClass;
        this.controlledSubstance = controlledSubstance;
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
