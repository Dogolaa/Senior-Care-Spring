package org.seniorcare.health.api.rest.dto.push_vitals;

import jakarta.validation.constraints.NotNull;
import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.util.UUID;

public class PushVitalsRequest {

    @NotNull
    private UUID residentId;

    @NotNull
    private UUID recordedById;

    private Integer heartRate;
    private Float saturation;
    private String bloodPressure;
    private Float temperature;

    @NotNull
    private VitalSignsSource source;

    public PushVitalsRequest() {
    }

    public UUID getResidentId() {
        return residentId;
    }

    public void setResidentId(UUID residentId) {
        this.residentId = residentId;
    }

    public UUID getRecordedById() {
        return recordedById;
    }

    public void setRecordedById(UUID recordedById) {
        this.recordedById = recordedById;
    }

    public Integer getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(Integer heartRate) {
        this.heartRate = heartRate;
    }

    public Float getSaturation() {
        return saturation;
    }

    public void setSaturation(Float saturation) {
        this.saturation = saturation;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public VitalSignsSource getSource() {
        return source;
    }

    public void setSource(VitalSignsSource source) {
        this.source = source;
    }
}
