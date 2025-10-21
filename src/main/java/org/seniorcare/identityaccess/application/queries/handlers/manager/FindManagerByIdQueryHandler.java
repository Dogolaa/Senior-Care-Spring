package org.seniorcare.identityaccess.application.queries.handlers.manager;

import org.seniorcare.identityaccess.application.dto.manager.ManagerDTO;
import org.seniorcare.identityaccess.application.queries.impl.manager.FindManagerByIdQuery;
import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IManagerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindManagerByIdQueryHandler {

    private final IManagerRepository managerRepository;

    public FindManagerByIdQueryHandler(IManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Transactional(readOnly = true)
    public Optional<ManagerDTO> handle(FindManagerByIdQuery query) {

        Optional<Manager> managerOptional = managerRepository.findById(query.id());
        return managerOptional.map(this::toDTO);
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