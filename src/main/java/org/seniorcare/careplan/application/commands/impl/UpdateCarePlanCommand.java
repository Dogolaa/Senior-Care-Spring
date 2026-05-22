package org.seniorcare.careplan.application.commands.impl;

import org.seniorcare.careplan.domain.vo.CarePlanStatus;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record UpdateCarePlanCommand(
        UUID id,
        String title,
        String description,
        List<String> goals,
        List<String> interventions,
        LocalDate startDate,
        LocalDate endDate,
        CarePlanStatus status
) {}
