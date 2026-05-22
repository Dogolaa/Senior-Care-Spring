package org.seniorcare.careplan.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class CreateCarePlanRequest {

    @NotNull
    private UUID residentId;

    @NotNull
    private UUID responsibleId;

    @NotBlank
    private String title;

    private String description;

    private List<String> goals;

    private List<String> interventions;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

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
}
