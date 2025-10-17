package org.seniorcare.identityaccess.infrastructure.persistence.jpa.mappers;

import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.ManagerModel;
import org.seniorcare.identityaccess.infrastructure.persistence.jpa.models.UserModel;
import org.springframework.stereotype.Component;

@Component
public class ManagerMapper implements EmployeeSubtypeMapper<Manager, ManagerModel> {

    private final UserMapper userMapper;

    public ManagerMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public Class<Manager> getEntityClass() {
        return Manager.class;
    }

    @Override
    public ManagerModel toModel(Manager entity) {
        if (entity == null) return null;

        UserModel userModel = userMapper.toModel(entity.getUser());

        ManagerModel model = new ManagerModel();
        model.setId(entity.getId());
        model.setUser(userModel);
        model.setAdmissionDate(entity.getAdmissionDate());

        model.setDepartment(entity.getDepartment());
        model.setShift(entity.getShift());
        model.setDeletedAt(entity.getDeletedAt());

        return model;
    }

    public Manager toEntity(ManagerModel model) {
        if (model == null) return null;

        User user = userMapper.toEntity(model.getUser());

        return new Manager(
                model.getId(),
                user,
                model.getAdmissionDate(),
                model.getDepartment(),
                model.getShift(),
                model.getDeletedAt()
        );
    }
}