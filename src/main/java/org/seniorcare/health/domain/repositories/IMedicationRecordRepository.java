package org.seniorcare.health.domain.repositories;

import org.seniorcare.health.domain.entities.MedicationRecord;
import org.seniorcare.health.domain.entities.MedicationRecordPhoto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IMedicationRecordRepository {

    MedicationRecord save(MedicationRecord medicationRecord);

    Optional<MedicationRecord> findById(UUID id);

    List<MedicationRecord> findByResidentId(UUID residentId);

    void addPhoto(UUID medicationRecordId, MedicationRecordPhoto photo);
}
