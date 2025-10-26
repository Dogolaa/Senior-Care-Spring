package org.seniorcare.residentmanagement.application.commands.impl.resident;

import org.seniorcare.residentmanagement.domain.vo.BloodType;
import org.seniorcare.residentmanagement.domain.vo.Gender;

import java.time.LocalDate;
import java.util.UUID;

public record AdmitResidentCommand(
        UUID responsibleId,
        String name,
        String cpfValue,
        String rgValue,
        LocalDate dateOfBirth,
        Gender gender,
        BloodType bloodType,
        String room
) {
}