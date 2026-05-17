package org.seniorcare.health.application.queries.handlers;

import org.seniorcare.health.application.queries.dto.ActivityRecordHistoryResponse;
import org.seniorcare.health.application.queries.dto.ActivityRecordResponse;
import org.seniorcare.health.application.queries.impl.FindActivityRecordByResidentIdQuery;
import org.seniorcare.health.domain.aggregates.ActivityRecord;
import org.seniorcare.health.domain.repositories.IActivityRecordRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindActivityRecordByResidentIdQueryHandler {

    private final IActivityRecordRepository activityRecordRepository;

    public FindActivityRecordByResidentIdQueryHandler(IActivityRecordRepository activityRecordRepository) {
        this.activityRecordRepository = activityRecordRepository;
    }

    public ActivityRecordResponse handle(FindActivityRecordByResidentIdQuery query) {
        ActivityRecord record = activityRecordRepository.findByResidentId(query.residentId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Activity record for resident " + query.residentId() + " not found."));

        List<ActivityRecordHistoryResponse> history = record.getHistory().stream()
                .map(h -> new ActivityRecordHistoryResponse(
                        h.getId(),
                        h.getActivityName(),
                        h.getDescription(),
                        h.getStartDateTime(),
                        h.getEndDateTime(),
                        h.getConductedById(),
                        h.getNotes(),
                        h.getRecordedAt(),
                        h.getPhotoUrls()))
                .collect(Collectors.toList());

        return new ActivityRecordResponse(
                record.getId(),
                record.getResidentId(),
                record.getConductedById(),
                record.getLastActivityDate(),
                history);
    }
}
