package org.seniorcare.identityaccess.application.queries.handlers.doctor;

import org.seniorcare.identityaccess.application.dto.doctor.DoctorDTO;
import org.seniorcare.identityaccess.application.queries.impl.doctor.FindAllDoctorsQuery;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.springframework.data.domain.Page;
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
        Page<Doctor> nmanagerPage = doctorRepository.findAll(query.pageable());
        return nmanagerPage.map(this::toDTO);
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