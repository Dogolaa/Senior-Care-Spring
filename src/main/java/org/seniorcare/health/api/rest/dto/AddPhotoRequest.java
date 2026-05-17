package org.seniorcare.health.api.rest.dto;

import jakarta.validation.constraints.NotBlank;

public class AddPhotoRequest {

    @NotBlank
    private String photoUrl;

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
