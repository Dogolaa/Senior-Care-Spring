package org.seniorcare.health.domain.entities;

import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class HealthRecordHistory {

    private UUID id;
    private UUID healthRecordId;
    private Float height;
    private Float weight;
    private String bloodPressure;
    private Integer heartRate;
    private Float temperature;
    private Float saturation;
    private Float imc;
    private LocalDate updateDate;
    private VitalSignsSource source;
    private List<String> photoUrls = new ArrayList<>();

    public HealthRecordHistory() {
    }

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getHealthRecordId() {
        return healthRecordId;
    }

    public void setHealthRecordId(UUID healthRecordId) {
        this.healthRecordId = healthRecordId;
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

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(LocalDate updateDate) {
        this.updateDate = updateDate;
    }

    public VitalSignsSource getSource() {
        return source;
    }

    public void setSource(VitalSignsSource source) {
        this.source = source;
    }

    public List<String> getPhotoUrls() {
        return photoUrls;
    }

    public void setPhotoUrls(List<String> photoUrls) {
        this.photoUrls = photoUrls;
    }
}
