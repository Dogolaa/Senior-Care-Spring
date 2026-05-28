package org.seniorcare.health.domain.entities;

import org.seniorcare.health.domain.vo.VitalSignsSource;
import org.seniorcare.shared.domain.Default;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthRecordHistory {

    private final UUID id;
    private final UUID healthRecordId;
    private final Float height;
    private final Float weight;
    private final String bloodPressure;
    private final Integer heartRate;
    private final Float temperature;
    private final Float saturation;
    private final Float imc;
    private final LocalDate updateDate;
    private final VitalSignsSource source;
    private final List<String> photoUrls;

    public HealthRecordHistory(UUID healthRecordId, Float height, Float weight, String bloodPressure,
            Integer heartRate, Float temperature, Float saturation, Float imc, LocalDate updateDate) {
        this(healthRecordId, height, weight, bloodPressure, heartRate, temperature, saturation, imc, updateDate,
                VitalSignsSource.MANUAL);
    }

    public HealthRecordHistory(UUID healthRecordId, Float height, Float weight, String bloodPressure,
            Integer heartRate, Float temperature, Float saturation, Float imc, LocalDate updateDate,
            VitalSignsSource source) {
        this.id = UUID.randomUUID();
        this.healthRecordId = healthRecordId;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.imc = imc;
        this.updateDate = updateDate;
        this.source = source;
        this.photoUrls = new ArrayList<>();
    }

    @Default
    public HealthRecordHistory(UUID id, UUID healthRecordId, Float height, Float weight,
            String bloodPressure, Integer heartRate, Float temperature, Float saturation,
            Float imc, LocalDate updateDate, VitalSignsSource source, List<String> photoUrls) {
        this.id = id;
        this.healthRecordId = healthRecordId;
        this.height = height;
        this.weight = weight;
        this.bloodPressure = bloodPressure;
        this.heartRate = heartRate;
        this.temperature = temperature;
        this.saturation = saturation;
        this.imc = imc;
        this.updateDate = updateDate;
        this.source = source;
        this.photoUrls = photoUrls != null ? photoUrls : new ArrayList<>();
    }

    public UUID getId() {
        return id;
    }

    public UUID getHealthRecordId() {
        return healthRecordId;
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public VitalSignsSource getSource() {
        return source;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }
}
