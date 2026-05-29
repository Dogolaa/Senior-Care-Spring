package org.seniorcare.residentmanagement.infrastructure.persistence;

import org.seniorcare.residentmanagement.application.ports.output.IUserExistencePort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class UserExistenceAdapter implements IUserExistencePort {

    private final JdbcTemplate jdbcTemplate;

    public UserExistenceAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean exists(UUID userId) {
        Integer count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM users WHERE id = ? AND deleted_at IS NULL",
                Integer.class,
                userId
        );
        return count != null && count > 0;
    }
}
