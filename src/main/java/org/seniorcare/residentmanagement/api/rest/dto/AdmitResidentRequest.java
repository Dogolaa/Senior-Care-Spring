package org.seniorcare.residentmanagement.api.rest.dto;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.*;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Gender;

import java.time.LocalDate;
import java.util.List;

public record AdmitResidentRequest(

        @NotBlank(message = "Nome completo é obrigatório.")
        @Size(min = 3, max = 255, message = "Nome deve ter entre 3 e 255 caracteres.")
        String name,

        @NotBlank(message = "CPF é obrigatório.")
        @Pattern(regexp = "^\\d{11}$", message = "CPF deve conter exatamente 11 dígitos numéricos.")
        String cpf,

        @NotBlank(message = "RG é obrigatório.")
        @Size(max = 20, message = "RG não pode exceder 20 caracteres.")
        String rg,

        @NotNull(message = "Data de nascimento é obrigatória.")
        @Past(message = "Data de nascimento deve ser no passado.")
        LocalDate dateOfBirth,

        @NotNull(message = "Gênero é obrigatório.")
        Gender gender,

        @NotNull(message = "Tipo sanguíneo é obrigatório.")
        BloodType bloodType,

        @Nullable
        List<String> initialAllergies,

        @NotBlank(message = "Número do quarto é obrigatório.")
        @Size(max = 50, message = "Número do quarto não pode exceder 50 caracteres.")
        String room
) {
}