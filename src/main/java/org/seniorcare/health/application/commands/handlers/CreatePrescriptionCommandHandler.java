package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.CreatePrescriptionCommand;
import org.seniorcare.health.domain.entities.Prescription;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.seniorcare.health.domain.repositories.IPrescriptionRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CreatePrescriptionCommandHandler {

    private final IPrescriptionRepository prescriptionRepository;
    private final IMedicationRepository medicationRepository;

    public CreatePrescriptionCommandHandler(IPrescriptionRepository prescriptionRepository,
                                            IMedicationRepository medicationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicationRepository = medicationRepository;
    }

    public UUID handle(CreatePrescriptionCommand command) {
        medicationRepository.findById(command.medicationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medication with id " + command.medicationId() + " not found."));

        Prescription newPrescription = new Prescription(
                command.medicalRecordId(),
                command.medicationId(),
                command.dosage(),
                command.startDate(),
                command.endDate()
        );

        Prescription savedPrescription = prescriptionRepository.save(newPrescription);
        return savedPrescription.getId();
    }
}
