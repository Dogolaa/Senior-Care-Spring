package org.seniorcare.identityaccess.application.queries.handlers.nurse;

import org.seniorcare.identityaccess.application.dto.nurse.NurseDTO;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindAllNursesQuery;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
        Pagination pagination = new Pagination(
                query.pageable().getPageNumber(),
                query.pageable().getPageSize()
        );
        PageResult<NurseDTO> result = nurseRepository.findAll(pagination).map(this::toDTO);
        return new PageImpl<>(
                result.content(),
                PageRequest.of(result.currentPage(), result.pageSize()),
                result.totalElements()
        );
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
