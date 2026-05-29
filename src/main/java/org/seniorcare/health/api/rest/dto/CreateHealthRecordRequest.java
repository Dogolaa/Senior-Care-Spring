package org.seniorcare.health.api.rest.dto;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public class CreateHealthRecordRequest {

    @NotNull(message = "ID do residente é obrigatório")
    private UUID residentId;

    @NotNull(message = "ID do responsável pelo registro é obrigatório")
    private UUID updatedById;

    @DecimalMin(value = "0.5", message = "Altura mínima: 0.5 m")
    @DecimalMax(value = "2.5", message = "Altura máxima: 2.5 m")
    private Float height;

    @DecimalMin(value = "1.0", message = "Peso mínimo: 1 kg")
    @DecimalMax(value = "500.0", message = "Peso máximo: 500 kg")
    private Float weight;

    @Size(max = 20, message = "Pressão arterial deve ter no máximo 20 caracteres")
    @Pattern(regexp = "^\\d{2,3}/\\d{2,3}$", message = "Formato de pressão arterial inválido (ex: 120/80)")
    private String bloodPressure;

    @Min(value = 20, message = "Frequência cardíaca mínima: 20 bpm")
    @Max(value = 300, message = "Frequência cardíaca máxima: 300 bpm")
    private Integer heartRate;

    @DecimalMin(value = "30.0", message = "Temperatura mínima: 30°C")
    @DecimalMax(value = "45.0", message = "Temperatura máxima: 45°C")
    private Float temperature;

    @DecimalMin(value = "0.0", message = "Saturação mínima: 0%")
    @DecimalMax(value = "100.0", message = "Saturação máxima: 100%")
    private Float saturation;

    public UUID getResidentId() {
        return residentId;
    }

    public void setResidentId(UUID residentId) {
        this.residentId = residentId;
    }

    public UUID getUpdatedById() {
        return updatedById;
    }

    public void setUpdatedById(UUID updatedById) {
        this.updatedById = updatedById;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getSaturation() {
        return saturation;
    }

    public void setSaturation(Float saturation) {
        this.saturation = saturation;
    }
}
