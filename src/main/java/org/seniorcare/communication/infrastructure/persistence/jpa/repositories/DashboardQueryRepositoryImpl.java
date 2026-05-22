package org.seniorcare.communication.infrastructure.persistence.jpa.repositories;

import org.seniorcare.communication.application.queries.dto.RecentActivityDTO;
import org.seniorcare.communication.application.queries.dto.RecentHealthUpdateDTO;
import org.seniorcare.communication.application.queries.ports.IDashboardQueryRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public class DashboardQueryRepositoryImpl implements IDashboardQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public DashboardQueryRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public long countActiveResidents() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM residents WHERE deleted_at IS NULL AND is_active = true",
                Long.class
        );
        return count != null ? count : 0L;
    }

    @Override
    public long countActiveEmployees() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM employees WHERE deleted_at IS NULL",
                Long.class
        );
        return count != null ? count : 0L;
    }

    @Override
    public long countMedicationsAdministeredToday() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM medication_records WHERE DATE(administration_date) = CURRENT_DATE",
                Long.class
        );
        return count != null ? count : 0L;
    }

    @Override
    public long countActivitiesLoggedToday() {
        Long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM activity_record_histories WHERE recorded_at = CURRENT_DATE",
                Long.class
        );
        return count != null ? count : 0L;
    }

    @Override
    public List<RecentHealthUpdateDTO> findRecentHealthUpdates(int limit) {
        String sql = """
                SELECT r.id, r.name, r.room, hr.last_updated
                FROM health_records hr
                JOIN residents r ON r.id = hr.resident_id AND r.deleted_at IS NULL
                ORDER BY hr.last_updated DESC
                LIMIT ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecentHealthUpdateDTO(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("room"),
                rs.getObject("last_updated", LocalDate.class)
        ), limit);
    }

    @Override
    public List<RecentActivityDTO> findRecentActivities(int limit) {
        String sql = """
                SELECT r.id, r.name, r.room, arh.activity_name, arh.start_date_time
                FROM activity_record_histories arh
                JOIN activity_records ar ON ar.id = arh.activity_record_id
                JOIN residents r ON r.id = ar.resident_id AND r.deleted_at IS NULL
                ORDER BY arh.start_date_time DESC
                LIMIT ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> new RecentActivityDTO(
                UUID.fromString(rs.getString("id")),
                rs.getString("name"),
                rs.getString("room"),
                rs.getString("activity_name"),
                rs.getObject("start_date_time", LocalDateTime.class)
        ), limit);
    }
}
