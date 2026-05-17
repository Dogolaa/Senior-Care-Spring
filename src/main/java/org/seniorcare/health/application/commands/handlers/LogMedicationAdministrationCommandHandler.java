package org.seniorcare.health.application.commands.handlers;

import org.seniorcare.health.application.commands.impl.LogMedicationAdministrationCommand;
import org.seniorcare.health.domain.entities.MedicationRecord;
import org.seniorcare.health.domain.repositories.IMedicationRecordRepository;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LogMedicationAdministrationCommandHandler {

    private final IMedicationRecordRepository medicationRecordRepository;
    private final IMedicationRepository medicationRepository;

    public LogMedicationAdministrationCommandHandler(IMedicationRecordRepository medicationRecordRepository,
                                                     IMedicationRepository medicationRepository) {
        this.medicationRecordRepository = medicationRecordRepository;
        this.medicationRepository = medicationRepository;
    }

    public UUID handle(LogMedicationAdministrationCommand command) {
        medicationRepository.findById(command.medicationId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Medication with id " + command.medicationId() + " not found."));

        MedicationRecord newRecord = new MedicationRecord(
                command.residentId(),
                command.medicationId(),
                command.administrationDate(),
                command.administeredById(),
                command.dose()
        );

        MedicationRecord savedRecord = medicationRecordRepository.save(newRecord);
        return savedRecord.getId();
    }
}
