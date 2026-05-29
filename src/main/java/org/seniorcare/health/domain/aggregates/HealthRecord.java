package org.seniorcare.health.domain.aggregates;

import org.seniorcare.health.domain.entities.HealthRecordHistory;
import org.seniorcare.health.domain.vo.VitalSignsSource;
import org.seniorcare.shared.domain.Default;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthRecord {

    private final UUID id;
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
    private List<HealthRecordHistory> history;
    private List<String> conditions;

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
        this.history = new ArrayList<>();
        this.conditions = new ArrayList<>();
        addHistoryEntry(VitalSignsSource.MANUAL);
    }

    @Default
    public HealthRecord(UUID id, UUID residentId, UUID updatedById, Float height, Float weight,
            String bloodPressure, Integer heartRate, Float temperature, Float saturation,
            Float imc, LocalDate lastUpdated, List<HealthRecordHistory> history, List<String> conditions) {
        this.id = id;
        this.residentId = residentId;
        this.updatedById = updatedById;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.imc = imc;
        this.lastUpdated = lastUpdated;
        this.history = history != null ? history : new ArrayList<>();
        this.conditions = conditions != null ? conditions : new ArrayList<>();
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

    public void addCondition(String conditionDescription) {
        if (conditionDescription == null || conditionDescription.isBlank()) return;
        String normalized = conditionDescription.trim().toUpperCase();
        if (!this.conditions.contains(normalized)) {
            this.conditions.add(normalized);
        }
    }

    public void removeCondition(String conditionDescription) {
        if (conditionDescription == null || conditionDescription.isBlank()) return;
        String normalized = conditionDescription.trim().toUpperCase();
        this.conditions.remove(normalized);
    }

    public UUID getId() {
        return id;
    }

    public UUID getResidentId() {
        return residentId;
    }

    public UUID getUpdatedById() {
        return updatedById;
    }

    public Float getHeight() {
        return height;
    }

    public Float getWeight() {
        return weight;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public Float getTemperature() {
        return temperature;
    }

    public Float getSaturation() {
        return saturation;
    }

    public Float getImc() {
        return imc;
    }

    public LocalDate getLastUpdated() {
        return lastUpdated;
    }

    public List<HealthRecordHistory> getHistory() {
        return history;
    }

    public List<String> getConditions() {
        return conditions;
    }
}
