package org.seniorcare.identityaccess.application.queries.handlers.nurse;

import org.seniorcare.identityaccess.application.dto.nurse.NurseDTO;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindNurseByIdQuery;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindNurseByIdQueryHandler {

    private final INurseRepository nurseRepository;

    public FindNurseByIdQueryHandler(INurseRepository nurseRepository) {
        this.nurseRepository = nurseRepository;
    }

    @Transactional(readOnly = true)
    public Optional<NurseDTO> handle(FindNurseByIdQuery query) {

        Optional<Nurse> nurseOptional = nurseRepository.findById(query.id());
        return nurseOptional.map(this::toDTO);
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