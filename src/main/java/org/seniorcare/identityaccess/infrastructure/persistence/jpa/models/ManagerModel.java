package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "managers")
@PrimaryKeyJoinColumn(name = "employee_id")
public class ManagerModel extends EmployeeModel {

    @Column(name = "department", nullable = true)
    private String department;

    @Column(name = "shift", nullable = false)
    private String shift;

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}