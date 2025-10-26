package org.seniorcare.residentmanagement.domain.vo;

public record Rg(String numero) {

    public Rg {
        if (numero == null || numero.trim().isEmpty()) {
            throw new IllegalArgumentException("RG não pode ser nulo ou vazio.");
        }

        numero = numero.replaceAll("[^a-zA-Z0-9]", "");

        if (numero.isEmpty()) {
            throw new IllegalArgumentException("RG inválido após formatação.");
        }
    }

    public String getNumero() {
        return this.numero;
    }
}