package org.seniorcare.health.api.rest.dto.activity_record;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public class CreateActivityRecordRequest {

    @NotNull
    private UUID residentId;

    @NotNull
    private UUID conductedById;

    public CreateActivityRecordRequest() {
    }

    public UUID getResidentId() {
        return residentId;
    }

    public void setResidentId(UUID residentId) {
        this.residentId = residentId;
    }

    public UUID getConductedById() {
        return conductedById;
    }

    public void setConductedById(UUID conductedById) {
        this.conductedById = conductedById;
    }
}
