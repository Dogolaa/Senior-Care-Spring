package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "nurses")
@PrimaryKeyJoinColumn(name = "employee_id")
public class NurseModel extends EmployeeModel {

    @Column(name = "coren", nullable = false, unique = true)
    private String coren;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "shift", nullable = false)
    private String shift;

    public String getCoren() {
        return coren;
    }

    public void setCoren(String coren) {
        this.coren = coren;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }
}