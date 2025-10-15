package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.DoctorModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class DoctorMapper implements EmployeeSubtypeMapper<Doctor, DoctorModel> {

    private final UserMapper userMapper;

    public DoctorMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Class<Doctor> getEntityClass() {
        return Doctor.class;
    }

    @Override
    public DoctorModel toModel(Doctor entity) {
        if (entity == null) return null;

        UserModel userModel = userMapper.toModel(entity.getUser());

        DoctorModel model = new DoctorModel();
        model.setId(entity.getId());
        model.setUser(userModel);
        model.setAdmissionDate(entity.getAdmissionDate());

        model.setCrm(entity.getCrm());
        model.setSpecialization(entity.getSpecialization());
        model.setShift(entity.getShift());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public Doctor toEntity(DoctorModel model) {
        if (model == null) return null;

        User user = userMapper.toEntity(model.getUser());

        return new Doctor(
                model.getId(),
                user,
                model.getAdmissionDate(),
                model.getCrm(),
                model.getSpecialization(),
                model.getShift(),
                model.getDeletedAt()
        );
    }
}