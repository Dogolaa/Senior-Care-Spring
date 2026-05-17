package org.seniorcare.residentmanagement.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Gender;

public record UpdateResidentRequest(
        @NotBlank(message = "Name is required")
        @Size(max = 255, message = "Name must be at most 255 characters")
        String name,

        @NotNull(message = "Gender is required")
        Gender gender,

        @NotNull(message = "Blood type is required")
        BloodType bloodType,

        @NotBlank(message = "Room is required")
        @Size(max = 50, message = "Room must be at most 50 characters")
        String room
) {
}
