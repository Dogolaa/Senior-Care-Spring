package org.seniorcare.careplan.application.commands.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record CreateCarePlanCommand(
        UUID residentId,
        UUID responsibleId,
        String title,
        String description,
        List<String> goals,
        List<String> interventions,
        LocalDate startDate,
        LocalDate endDate
) {}
