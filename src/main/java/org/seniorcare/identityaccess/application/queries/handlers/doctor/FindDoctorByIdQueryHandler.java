package org.seniorcare.identityaccess.application.queries.handlers.doctor;

import org.seniorcare.identityaccess.application.dto.doctor.DoctorDTO;
import org.seniorcare.identityaccess.application.queries.impl.doctor.FindDoctorByIdQuery;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FindDoctorByIdQueryHandler {

    private final IDoctorRepository doctorRepository;

    public FindDoctorByIdQueryHandler(IDoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    @Transactional(readOnly = true)
    public Optional<DoctorDTO> handle(FindDoctorByIdQuery query) {

        Optional<Doctor> doctorOptional = doctorRepository.findById(query.id());
        return doctorOptional.map(this::toDTO);
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