package org.seniorcare.health.application.queries.handlers;

import org.seniorcare.health.application.queries.dto.HealthRecordHistoryResponse;
import org.seniorcare.health.application.queries.dto.HealthRecordResponse;
import org.seniorcare.health.application.queries.impl.FindHealthRecordByResidentIdQuery;
import org.seniorcare.health.domain.aggregates.HealthRecord;
import org.seniorcare.health.domain.repositories.IHealthRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindHealthRecordByResidentIdQueryHandler {

    private final IHealthRecordRepository healthRecordRepository;

    public FindHealthRecordByResidentIdQueryHandler(IHealthRecordRepository healthRecordRepository) {
        this.healthRecordRepository = healthRecordRepository;
    }

    public HealthRecordResponse handle(FindHealthRecordByResidentIdQuery query) {
        HealthRecord record = healthRecordRepository.findByResidentId(query.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Health record for resident " + query.residentId() + " not found."));

        List<HealthRecordHistoryResponse> history = record.getHistory().stream()
                .map(h -> new HealthRecordHistoryResponse(
                        h.getId(),
                        h.getHeight(),
                        h.getWeight(),
                        h.getBloodPressure(),
                        h.getHeartRate(),
                        h.getTemperature(),
                        h.getSaturation(),
                        h.getImc(),
                        h.getUpdateDate(),
                        h.getSource(),
                        h.getPhotoUrls()))
                .collect(Collectors.toList());

        return new HealthRecordResponse(
                record.getId(),
                record.getResidentId(),
                record.getUpdatedById(),
                record.getHeight(),
                record.getWeight(),
                record.getBloodPressure(),
                record.getHeartRate(),
                record.getTemperature(),
                record.getSaturation(),
                record.getImc(),
                record.getLastUpdated(),
                history);
    }
}
