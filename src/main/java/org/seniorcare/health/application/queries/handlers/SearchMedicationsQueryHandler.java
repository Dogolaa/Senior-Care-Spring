package org.seniorcare.health.application.queries.handlers;

import org.seniorcare.health.application.queries.dto.MedicationResponse;
import org.seniorcare.health.application.queries.impl.SearchMedicationsQuery;
import org.seniorcare.health.domain.repositories.IMedicationRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchMedicationsQueryHandler {

    private final IMedicationRepository medicationRepository;

    public SearchMedicationsQueryHandler(IMedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public List<MedicationResponse> handle(SearchMedicationsQuery query) {
        return medicationRepository.search(query.productName(), query.page(), query.count())
                .stream()
                .map(m -> new MedicationResponse(
                        m.getId(),
                        m.getCommercialName(),
                        m.getActiveIngredient(),
                        m.getPharmaceuticalForm(),
                        m.getConcentration(),
                        m.getManufacturer(),
                        m.getRegistrationNumber(),
                        m.getTherapeuticClass(),
                        m.isControlledSubstance()))
                .collect(Collectors.toList());
    }
}
