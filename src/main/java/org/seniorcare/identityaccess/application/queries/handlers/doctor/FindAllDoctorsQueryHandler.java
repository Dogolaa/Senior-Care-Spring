package org.seniorcare.identityaccess.application.queries.handlers.doctor;

import org.seniorcare.identityaccess.application.dto.doctor.DoctorDTO;
import org.seniorcare.identityaccess.application.queries.impl.doctor.FindAllDoctorsQuery;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.seniorcare.shared.domain.PageResult;
import org.seniorcare.shared.domain.Pagination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FindAllDoctorsQueryHandler {

    private final IDoctorRepository doctorRepository;

    public FindAllDoctorsQueryHandler(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public Page<DoctorDTO> handle(FindAllDoctorsQuery query) {
        Pagination pagination = new Pagination(
                query.pageable().getPageNumber(),
                query.pageable().getPageSize()
        );
        PageResult<DoctorDTO> result = doctorRepository.findAll(pagination).map(this::toDTO);
        return new PageImpl<>(
                result.content(),
                PageRequest.of(result.currentPage(), result.pageSize()),
                result.totalElements()
        );
    }

    private DoctorDTO toDTO(Doctor doctor) {
        User user = doctor.getUser();
        return new DoctorDTO(
                user.getId(),
                doctor.getId(),
                user.getName(),
                user.getEmail().value(),
                user.getPhone(),
                doctor.getCrm(),
                doctor.getSpecialization(),
                doctor.getShift(),
                user.isActive(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
