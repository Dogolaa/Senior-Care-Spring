package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.DoctorModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.EmployeeModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.NurseModel;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

    private final UserMapper userMapper;

    public EmployeeMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    // TODO: Isso aqui ta violando SOLID, talvez o trade-off valha a pena, analisar.
    public Employee toEntity(EmployeeModel model) {
        if (model == null) return null;

        if (model instanceof NurseModel nurseModel) {
            User user = userMapper.toEntity(nurseModel.getUser());
            return new Nurse(
                    nurseModel.getId(),
                    user,
                    nurseModel.getAdmissionDate(),
                    nurseModel.getCoren(),
                    nurseModel.getSpecialization(),
                    nurseModel.getShift(),
                    nurseModel.getDeletedAt()
            );
        } else if (model instanceof DoctorModel doctorModel) {
            User user = userMapper.toEntity(doctorModel.getUser());
            return new Doctor(
                    doctorModel.getId(),
                    user,
                    doctorModel.getAdmissionDate(),
                    doctorModel.getCrm(),
                    doctorModel.getSpecialization(),
                    doctorModel.getShift(),
                    doctorModel.getDeletedAt()
            );
        }


        throw new IllegalArgumentException("Unknown EmployeeModel subtype: " + model.getClass().getName());
    }
}