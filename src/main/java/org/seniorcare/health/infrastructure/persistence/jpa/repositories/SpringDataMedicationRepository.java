package org.seniorcare.health.infrastructure.persistence.jpa.repositories;

import org.seniorcare.health.infrastructure.persistence.jpa.models.MedicationModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SpringDataMedicationRepository extends JpaRepository<MedicationModel, UUID> {

    Page<MedicationModel> findByCommercialNameContainingIgnoreCaseOrActiveIngredientContainingIgnoreCase(
            String commercialName, String activeIngredient, Pageable pageable);
}
