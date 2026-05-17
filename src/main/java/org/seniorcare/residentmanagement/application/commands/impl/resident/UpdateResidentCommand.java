package org.seniorcare.residentmanagement.application.commands.impl.resident;

import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Gender;

import java.util.UUID;

public record UpdateResidentCommand(
        UUID id,
        String name,
        Gender gender,
        BloodType bloodType,
        String room
) {
}
