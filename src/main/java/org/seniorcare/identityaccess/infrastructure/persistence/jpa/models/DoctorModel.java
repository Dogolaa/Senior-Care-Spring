package org.seniorcare.identityaccess.infrastructure.persistence.jpa.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "employee_id")
public class DoctorModel extends EmployeeModel {

    @Column(name = "crm", nullable = false, unique = true)
    private String crm;

    @Column(name = "specialization", nullable = false)
    private String specialization;

    @Column(name = "shift", nullable = false)
    private String shift;

    public String getCrm() {
        return crm;
    }

    public void setCrm(String crm) {
        this.crm = crm;
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
