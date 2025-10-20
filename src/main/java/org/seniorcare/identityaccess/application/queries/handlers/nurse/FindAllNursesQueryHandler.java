package org.seniorcare.identityaccess.application.queries.handlers.nurse;

import org.seniorcare.identityaccess.application.dto.nurse.NurseDTO;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindAllNursesQuery;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllNursesQueryHandler {

    private final INurseRepository nurseRepository;

    public FindAllNursesQueryHandler(INurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Transactional(readOnly = true)
    public Page<NurseDTO> handle(FindAllNursesQuery query) {
        Page<Nurse> nursePage = nurseRepository.findAll(query.pageable());
        return nursePage.map(this::toDTO);
    }
    
    private NurseDTO toDTO(Nurse nurse) {
        User user = nurse.getUser();

        return new NurseDTO(
                user.getId(),
                nurse.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getPhone(),
                nurse.getCoren(),
                nurse.getSpecialization(),
                nurse.getShift(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}