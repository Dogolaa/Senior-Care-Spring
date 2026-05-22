package org.seniorcare.careplan.domain.aggregates;

import org.seniorcare.careplan.domain.vo.CarePlanStatus;
import org.seniorcare.shared.exceptions.BadRequestException;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CarePlan {

    private final UUID id;
    private UUID residentId;
    private UUID responsibleId;
    private String title;
    private String description;
    private List<String> goals;
    private List<String> interventions;
    private LocalDate startDate;
    private LocalDate endDate;
    private CarePlanStatus status;
    private Instant createdAt;
    private Instant updatedAt;

    private CarePlan(UUID residentId, UUID responsibleId, String title, String description,
            List<String> goals, List<String> interventions, LocalDate startDate, LocalDate endDate) {

        if (residentId == null) throw new BadRequestException("ResidentId cannot be null.");
        if (responsibleId == null) throw new BadRequestException("ResponsibleId cannot be null.");
        if (title == null || title.isBlank()) throw new BadRequestException("Title cannot be empty.");
        if (startDate == null) throw new BadRequestException("StartDate cannot be null.");

        this.id = UUID.randomUUID();
        this.residentId = residentId;
        this.responsibleId = responsibleId;
        this.title = title.trim();
        this.description = description != null ? description.trim() : null;
        this.goals = goals != null ? goals : List.of();
        this.interventions = interventions != null ? interventions : List.of();
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = CarePlanStatus.ACTIVE;
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public CarePlan(UUID id, UUID residentId, UUID responsibleId, String title, String description,
            List<String> goals, List<String> interventions, LocalDate startDate, LocalDate endDate,
            CarePlanStatus status, Instant createdAt, Instant updatedAt) {
        if (id == null) throw new IllegalArgumentException("ID cannot be null when reconstituting.");
        this.id = id;
        this.residentId = residentId;
        this.responsibleId = responsibleId;
        this.title = title;
        this.description = description;
        this.goals = goals != null ? goals : List.of();
        this.interventions = interventions != null ? interventions : List.of();
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static CarePlan create(UUID residentId, UUID responsibleId, String title, String description,
            List<String> goals, List<String> interventions, LocalDate startDate, LocalDate endDate) {
        return new CarePlan(residentId, responsibleId, title, description, goals, interventions, startDate, endDate);
    }

    public void update(String title, String description, List<String> goals, List<String> interventions,
            LocalDate startDate, LocalDate endDate, CarePlanStatus status) {
        if (title == null || title.isBlank()) throw new BadRequestException("Title cannot be empty.");
        if (startDate == null) throw new BadRequestException("StartDate cannot be null.");
        this.title = title.trim();
        this.description = description != null ? description.trim() : null;
        this.goals = goals != null ? goals : List.of();
        this.interventions = interventions != null ? interventions : List.of();
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status != null ? status : this.status;
        this.updatedAt = Instant.now();
    }

    public UUID getId() { return id; }
    public UUID getResidentId() { return residentId; }
    public UUID getResponsibleId() { return responsibleId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public List<String> getGoals() { return goals; }
    public List<String> getInterventions() { return interventions; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }
    public CarePlanStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
}
