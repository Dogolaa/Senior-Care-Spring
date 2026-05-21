package org.seniorcare.identityaccess.application.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.time.Instant;
import java.util.UUID;

public class UserDTO extends RepresentationModel<UserDTO> {

    private final UUID id;
    private final String name;
    private final String email;
    private final String phone;
    private final boolean isActive;
    private final String role;
    private final String photoUrl;
    private final Instant createdAt;
    private final Instant updatedAt;

    public UserDTO(UUID id, String name, String email, String phone, boolean isActive, String role, String photoUrl, Instant createdAt, Instant updatedAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.isActive = isActive;
        this.role = role;
        this.photoUrl = photoUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
    @JsonProperty("isActive")
    public boolean isActive() { return isActive; }
    public String getRole() { return role; }
    public String getPhotoUrl() { return photoUrl; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
