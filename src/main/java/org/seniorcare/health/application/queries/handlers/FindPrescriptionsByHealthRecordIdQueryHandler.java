package org.seniorcare.health.application.queries.handlers;

import org.seniorcare.health.application.queries.dto.PrescriptionResponse;
import org.seniorcare.health.application.queries.impl.FindPrescriptionsByHealthRecordIdQuery;
import org.seniorcare.health.domain.entities.Medication;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.seniorcare.health.domain.repositories.IPrescriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindPrescriptionsByHealthRecordIdQueryHandler {

    private final IPrescriptionRepository prescriptionRepository;
    private final IMedicationRepository medicationRepository;

    public FindPrescriptionsByHealthRecordIdQueryHandler(IPrescriptionRepository prescriptionRepository,
                                                         IMedicationRepository medicationRepository) {
        this.prescriptionRepository = prescriptionRepository;
        this.medicationRepository = medicationRepository;
    }

    public List<PrescriptionResponse> handle(FindPrescriptionsByHealthRecordIdQuery query) {
        return prescriptionRepository.findByHealthRecordId(query.healthRecordId())
                .stream()
                .map(prescription -> {
                    String medicationName = medicationRepository.findById(prescription.getMedicationId())
                            .map(Medication::getCommercialName)
                            .orElse("Medicamento não encontrado");

                    return new PrescriptionResponse(
                            prescription.getId(),
                            prescription.getMedicalRecordId(),
                            prescription.getMedicationId(),
                            medicationName,
                            prescription.getDosage(),
                            prescription.getStartDate(),
                            prescription.getEndDate());
                })
                .collect(Collectors.toList());
    }
}
