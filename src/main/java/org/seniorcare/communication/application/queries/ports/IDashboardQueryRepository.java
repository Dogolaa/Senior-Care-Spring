package org.seniorcare.communication.application.queries.ports;

import org.seniorcare.communication.application.queries.dto.RecentActivityDTO;
import org.seniorcare.communication.application.queries.dto.RecentHealthUpdateDTO;

import java.util.List;

public interface IDashboardQueryRepository {
    long countActiveResidents();
    long countActiveEmployees();
    long countMedicationsAdministeredToday();
    long countActivitiesLoggedToday();
    List<RecentHealthUpdateDTO> findRecentHealthUpdates(int limit);
    List<RecentActivityDTO> findRecentActivities(int limit);
}
