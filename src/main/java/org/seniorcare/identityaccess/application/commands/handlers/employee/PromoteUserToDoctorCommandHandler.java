package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToDoctorCommand;
import org.seniorcare.identityaccess.domain.entities.Doctor;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IDoctorRepository;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PromoteUserToDoctorCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;
    private final IDoctorRepository doctorRepository;

    public PromoteUserToDoctorCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository, IEmployeeRepository employeeRepository, IDoctorRepository doctorRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.doctorRepository = doctorRepository;
    }

    @Transactional
    public void handle(PromoteUserToDoctorCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User to be promoted not found with id: " + command.userId()));

        Optional<Employee> existingEmployeeOpt = employeeRepository.findByUserIdIncludingDeleted(user.getId());

        Optional<Doctor> doctorWithSameCrm = doctorRepository.findByCrm(command.crm());
        if (doctorWithSameCrm.isPresent()) {
            if (existingEmployeeOpt.isEmpty() || !existingEmployeeOpt.get().getId().equals(doctorWithSameCrm.get().getId())) {
                throw new BadRequestException("CRM '" + command.crm() + "' is already in use by another doctor.");
            }
        }


        Role doctorRole = roleRepository.findByName("DOCTOR")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'DOCTOR' not found. Please seed the database."));

        user.changeRole(doctorRole.getId());

        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            if (existingEmployee.getDeletedAt() == null) {
                throw new BadRequestException("User with id " + user.getId() + " is already an active employee.");
            }

            existingEmployee.reactivate();

            if (existingEmployee instanceof Doctor doctorToUpdate) {
                doctorToUpdate.updateDetails(command.crm(), command.specialization(), command.shift());
            }

            employeeRepository.save(existingEmployee);
        } else {
            Doctor newDoctor = Doctor.create(
                    user,
                    command.admissionDate(),
                    command.crm(),
                    command.specialization(),
                    command.shift()
            );
            employeeRepository.save(newDoctor);
        }

        userRepository.save(user);
    }
}