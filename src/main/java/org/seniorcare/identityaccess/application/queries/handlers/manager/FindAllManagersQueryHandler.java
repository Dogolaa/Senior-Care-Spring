package org.seniorcare.identityaccess.application.queries.handlers.manager;

import org.seniorcare.identityaccess.application.dto.manager.ManagerDTO;
import org.seniorcare.identityaccess.application.queries.impl.manager.FindAllManagersQuery;
import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IManagerRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllManagersQueryHandler {

    private final IManagerRepository managerRepository;

    public FindAllManagersQueryHandler(IManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Transactional(readOnly = true)
    public Page<ManagerDTO> handle(FindAllManagersQuery query) {
        Page<Manager> managerPage = managerRepository.findAll(query.pageable());
        return managerPage.map(this::toDTO);
    }

    private ManagerDTO toDTO(Manager manager) {
        User user = manager.getUser();

        return new ManagerDTO(
                user.getId(),
                manager.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getPhone(),
                manager.getDepartment(),
                manager.getShift(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}