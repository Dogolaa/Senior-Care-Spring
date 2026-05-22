package org.seniorcare.careplan.infrastructure.persistence.jpa.models;

import jakarta.persistence.*;
import org.seniorcare.careplan.domain.vo.CarePlanStatus;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "care_plans")
public class CarePlanModel {

    @Id
    private UUID id;

    @Column(name = "resident_id", nullable = false)
    private UUID residentId;

    @Column(name = "responsible_id", nullable = false)
    private UUID responsibleId;

    @Column(name = "title", nullable = false, length = 255)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "care_plan_goals", joinColumns = @JoinColumn(name = "care_plan_id"))
    @Column(name = "goal", columnDefinition = "TEXT")
    @OrderColumn(name = "goal_order")
    private List<String> goals = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "care_plan_interventions", joinColumns = @JoinColumn(name = "care_plan_id"))
    @Column(name = "intervention", columnDefinition = "TEXT")
    @OrderColumn(name = "intervention_order")
    private List<String> interventions = new ArrayList<>();

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private CarePlanStatus status;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    public CarePlanModel() {}

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getResidentId() { return residentId; }
    public void setResidentId(UUID residentId) { this.residentId = residentId; }

    public UUID getResponsibleId() { return responsibleId; }
    public void setResponsibleId(UUID responsibleId) { this.responsibleId = responsibleId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getGoals() { return goals; }
    public void setGoals(List<String> goals) { this.goals = goals; }

    public List<String> getInterventions() { return interventions; }
    public void setInterventions(List<String> interventions) { this.interventions = interventions; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public CarePlanStatus getStatus() { return status; }
    public void setStatus(CarePlanStatus status) { this.status = status; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }

    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
