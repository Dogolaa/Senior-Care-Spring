package org.seniorcare.careplan.api.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.seniorcare.careplan.domain.vo.CarePlanStatus;

import java.time.LocalDate;
import java.util.List;

public class UpdateCarePlanRequest {

    @NotBlank
    private String title;

    private String description;

    private List<String> goals;

    private List<String> interventions;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private CarePlanStatus status;

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
}
