package org.seniorcare.health.application.queries.handlers;

import org.seniorcare.health.application.queries.dto.MedicationRecordResponse;
import org.seniorcare.health.application.queries.impl.FindMedicationRecordsByResidentIdQuery;
import org.seniorcare.health.domain.entities.Medication;
import org.seniorcare.health.domain.repositories.IMedicationRecordRepository;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindMedicationRecordsByResidentIdQueryHandler {

    private final IMedicationRecordRepository medicationRecordRepository;
    private final IMedicationRepository medicationRepository;

    public FindMedicationRecordsByResidentIdQueryHandler(IMedicationRecordRepository medicationRecordRepository,
                                                          IMedicationRepository medicationRepository) {
        this.medicationRecordRepository = medicationRecordRepository;
        this.medicationRepository = medicationRepository;
    }

    public List<MedicationRecordResponse> handle(FindMedicationRecordsByResidentIdQuery query) {
        return medicationRecordRepository.findByResidentId(query.residentId())
                .stream()
                .map(record -> {
                    String medicationName = medicationRepository.findById(record.getMedicationId())
                            .map(Medication::getCommercialName)
                            .orElse("Medicamento não encontrado");

                    return new MedicationRecordResponse(
                            record.getId(),
                            record.getResidentId(),
                            record.getMedicationId(),
                            medicationName,
                            record.getAdministrationDate(),
                            record.getAdministeredById(),
                            record.getDose(),
                            record.getPhotoUrls());
                })
                .collect(Collectors.toList());
    }
}
