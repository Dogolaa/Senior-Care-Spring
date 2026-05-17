package org.seniorcare.health.domain.aggregates;

import org.seniorcare.health.domain.entities.HealthRecordHistory;
import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthRecord {

    private UUID id;
    private UUID residentId;
    private UUID updatedById;
    private Float height;
    private Float weight;
    private String bloodPressure;
    private Integer heartRate;
    private Float temperature;
    private Float saturation;
    private Float imc;
    private LocalDate lastUpdated;
    private List<HealthRecordHistory> history = new ArrayList<>();

    public HealthRecord() {
    }

    public HealthRecord(UUID residentId, UUID updatedById, Float height, Float weight, String bloodPressure,
            Integer heartRate, Float temperature, Float saturation) {
        this.id = UUID.randomUUID();
        this.residentId = residentId;
        this.updatedById = updatedById;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.imc = calculateImc(weight, height);
        this.lastUpdated = LocalDate.now();
        addHistoryEntry(VitalSignsSource.MANUAL);
    }

    public void update(UUID updatedById, Float height, Float weight, String bloodPressure, Integer heartRate,
            Float temperature, Float saturation) {
        this.updatedById = updatedById;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.imc = calculateImc(weight, height);
        this.lastUpdated = LocalDate.now();
        addHistoryEntry(VitalSignsSource.MANUAL);
    }

    public void recordVitals(UUID recordedById, Integer heartRate, Float saturation,
                              String bloodPressure, Float temperature, VitalSignsSource source) {
        if (heartRate != null) this.heartRate = heartRate;
        if (saturation != null) this.saturation = saturation;
        if (bloodPressure != null) this.bloodPressure = bloodPressure;
        if (temperature != null) this.temperature = temperature;
        this.imc = calculateImc(this.weight, this.height);
        this.updatedById = recordedById;
        this.lastUpdated = LocalDate.now();
        addHistoryEntry(source);
    }

    private Float calculateImc(Float weight, Float height) {
        if (weight == null || height == null || height == 0) {
            return null;
        }
        return weight / (height * height);
    }

    private void addHistoryEntry(VitalSignsSource source) {
        HealthRecordHistory entry = new HealthRecordHistory(
                this.id,
                this.height,
                this.weight,
                this.bloodPressure,
                this.heartRate,
                this.temperature,
                this.saturation,
                this.imc,
                LocalDate.now(),
                source);
        this.history.add(entry);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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

    public Float getImc() {
        return imc;
    }

    public void setImc(Float imc) {
        this.imc = imc;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDate lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public List<HealthRecordHistory> getHistory() {
        return history;
    }

    public void setHistory(List<HealthRecordHistory> history) {
        this.history = history;
    }
}
