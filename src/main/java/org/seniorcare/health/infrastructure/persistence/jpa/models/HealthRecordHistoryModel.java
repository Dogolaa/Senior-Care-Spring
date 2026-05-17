package org.seniorcare.health.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.seniorcare.health.domain.vo.VitalSignsSource;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "health_record_histories")
public class HealthRecordHistoryModel {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_record_id")
    private HealthRecordModel healthRecord;

    private Float height;
    private Float weight;
    private String bloodPressure;
    private Integer heartRate;
    private Float temperature;
    private Float saturation;
    private Float imc;
    private LocalDate updateDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "source", nullable = false)
    private VitalSignsSource source = VitalSignsSource.MANUAL;

    @OneToMany(mappedBy = "healthRecordHistory", fetch = FetchType.EAGER)
    private List<HealthRecordPhotoModel> photos = new ArrayList<>();

    public HealthRecordHistoryModel() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public HealthRecordModel getHealthRecord() {
        return healthRecord;
    }

    public void setHealthRecord(HealthRecordModel healthRecord) {
        this.healthRecord = healthRecord;
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

    public List<HealthRecordPhotoModel> getPhotos() {
        return photos;
    }

    public void setPhotos(List<HealthRecordPhotoModel> photos) {
        this.photos = photos;
    }
}
