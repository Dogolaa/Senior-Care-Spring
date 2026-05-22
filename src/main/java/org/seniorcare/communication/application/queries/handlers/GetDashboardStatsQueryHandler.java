package org.seniorcare.communication.application.queries.handlers;

import org.seniorcare.communication.application.queries.dto.DashboardStatsResponse;
import org.seniorcare.communication.application.queries.impl.GetDashboardStatsQuery;
import org.seniorcare.communication.application.queries.ports.IDashboardQueryRepository;
import org.springframework.stereotype.Service;

@Service
public class GetDashboardStatsQueryHandler {

    private final IDashboardQueryRepository repository;

    public GetDashboardStatsQueryHandler(IDashboardQueryRepository repository) {
        this.repository = repository;
    }

    public DashboardStatsResponse handle(GetDashboardStatsQuery query) {
        return new DashboardStatsResponse(
                repository.countActiveResidents(),
                repository.countActiveEmployees(),
                repository.countMedicationsAdministeredToday(),
                repository.countActivitiesLoggedToday(),
                repository.findRecentHealthUpdates(5),
                repository.findRecentActivities(5)
        );
    }
}
