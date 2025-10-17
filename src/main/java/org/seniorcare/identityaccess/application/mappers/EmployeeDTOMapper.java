package org.seniorcare.identityaccess.application.mappers;

import org.seniorcare.identityaccess.application.dto.employee.EmployeeDTO;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDTOMapper {

    public EmployeeDTO toDTO(Employee employee) {
        if (employee instanceof Doctor doctor) {
            return new EmployeeDTO(
                    doctor.getId(),
                    doctor.getUser().getId(),
                    "Doctor",
                    doctor.getAdmissionDate(),
                    doctor.getSpecialization(),
                    doctor.getShift(),
                    doctor.getCrm(),
                    null,
                    null
            );
        } else if (employee instanceof Nurse nurse) {
            return new EmployeeDTO(
                    nurse.getId(),
                    nurse.getUser().getId(),
                    "Nurse",
                    nurse.getAdmissionDate(),
                    nurse.getSpecialization(),
                    nurse.getShift(),
                    null,
                    nurse.getCoren(),
                    null
            );
        } else if (employee instanceof Manager manager) {
            return new EmployeeDTO(
                    manager.getId(),
                    manager.getUser().getId(),
                    "Manager",
                    manager.getAdmissionDate(),
                    null,
                    manager.getShift(),
                    null,
                    null,
                    manager.getDepartment()
            );
        }
        throw new IllegalArgumentException("Tipo de funcionário desconhecido para conversão em DTO: " + employee.getClass().getName());
    }
}