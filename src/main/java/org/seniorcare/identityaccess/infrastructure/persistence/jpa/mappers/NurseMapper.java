package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.NurseModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class NurseMapper implements EmployeeSubtypeMapper<Nurse, NurseModel> {

    private final UserMapper userMapper;

    public NurseMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Class<Nurse> getEntityClass() {
        return Nurse.class;
    }

    @Override
    public NurseModel toModel(Nurse entity) {
        if (entity == null) return null;

        UserModel userModel = userMapper.toModel(entity.getUser());

        NurseModel model = new NurseModel();
        model.setId(entity.getId());
        model.setUser(userModel);
        model.setAdmissionDate(entity.getAdmissionDate());

        model.setCoren(entity.getCoren());
        model.setSpecialization(entity.getSpecialization());
        model.setShift(entity.getShift());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public Nurse toEntity(NurseModel model) {
        if (model == null) return null;

        User user = userMapper.toEntity(model.getUser());

        return new Nurse(
                model.getId(),
                user,
                model.getAdmissionDate(),
                model.getCoren(),
                model.getSpecialization(),
                model.getShift(),
                model.getDeletedAt()
        );
    }
}