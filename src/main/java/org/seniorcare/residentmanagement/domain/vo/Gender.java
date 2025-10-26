package org.seniorcare.residentmanagement.domain.vo;

public enum Gender {

    MASCULINO("Masculino"),
    FEMININO("Feminino"),
    OUTRO("Outro"),
    NAO_INFORMADO("Não Informado");

    private final String description;

    Gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public String getName() {
        return this.name();
    }

    @Override
    public String toString() {
        return this.description;
    }
}