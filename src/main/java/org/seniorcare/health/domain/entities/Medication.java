package org.seniorcare.health.domain.entities;

import java.util.UUID;

public class Medication {

    private final UUID id;
    private final String commercialName;
    private final String activeIngredient;
    private final String pharmaceuticalForm;
    private final String concentration;
    private final String manufacturer;
    private final String registrationNumber;
    private final String therapeuticClass;
    private final boolean controlledSubstance;

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

    public String getCommercialName() {
        return commercialName;
    }

    public String getActiveIngredient() {
        return activeIngredient;
    }

    public String getPharmaceuticalForm() {
        return pharmaceuticalForm;
    }

    public String getConcentration() {
        return concentration;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public String getTherapeuticClass() {
        return therapeuticClass;
    }

    public boolean isControlledSubstance() {
        return controlledSubstance;
    }
}
