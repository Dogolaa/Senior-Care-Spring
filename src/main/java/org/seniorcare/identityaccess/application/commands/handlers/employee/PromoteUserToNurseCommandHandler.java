package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToNurseCommand;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromoteUserToNurseCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;

    public PromoteUserToNurseCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository, IEmployeeRepository employeeRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(PromoteUserToNurseCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User to be promoted not found with id: " + command.userId()));

        if (employeeRepository.findByUserId(user.getId()).isPresent()) {
            throw new BadRequestException("User with id " + user.getId() + " is already an employee.");
        }

        Role nurseRole = roleRepository.findByName("NURSE")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'NURSE' not found. Please seed the database."));

        user.changeRole(nurseRole.getId());

        Nurse newNurse = Nurse.create(
                user,
                command.admissionDate(),
                command.coren(),
                command.specialization(),
                command.shift()
        );
        
        userRepository.save(user);
        employeeRepository.save(newNurse);
    }
}