package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Nurse;
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

        return model;
    }
}