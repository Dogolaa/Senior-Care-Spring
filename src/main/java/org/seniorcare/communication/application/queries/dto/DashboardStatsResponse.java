package org.seniorcare.communication.application.queries.dto;

import java.util.List;

public record DashboardStatsResponse(
        long totalActiveResidents,
        long totalEmployees,
        long medicationsAdministeredToday,
        long activitiesLoggedToday,
        List<RecentHealthUpdateDTO> recentHealthUpdates,
        List<RecentActivityDTO> recentActivities
) {
}
