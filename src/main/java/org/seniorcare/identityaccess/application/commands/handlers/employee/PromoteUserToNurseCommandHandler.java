package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToNurseCommand;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Nurse;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.INurseRepository;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PromoteUserToNurseCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;
    private final INurseRepository nurseRepository;

    public PromoteUserToNurseCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository, IEmployeeRepository employeeRepository, INurseRepository nurseRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.nurseRepository = nurseRepository;
    }

    @Transactional
    public void handle(PromoteUserToNurseCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User to be promoted not found with id: " + command.userId()));

        Optional<Employee> existingEmployeeOpt = employeeRepository.findByUserIdIncludingDeleted(user.getId());

        Optional<Nurse> nurseWithSameCoren = nurseRepository.findByCoren(command.coren());
        if (nurseWithSameCoren.isPresent()) {
            if (existingEmployeeOpt.isEmpty() || !existingEmployeeOpt.get().getId().equals(nurseWithSameCoren.get().getId())) {
                throw new BadRequestException("COREN '" + command.coren() + "' is already in use by another nurse.");
            }
        }
        

        Role nurseRole = roleRepository.findByName("NURSE")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'NURSE' not found. Please seed the database."));

        user.changeRole(nurseRole.getId());

        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            if (existingEmployee.getDeletedAt() == null) {
                throw new BadRequestException("User with id " + user.getId() + " is already an active employee.");
            }

            existingEmployee.reactivate();

            if (existingEmployee instanceof Nurse nurseToUpdate) {
                nurseToUpdate.updateDetails(command.coren(), command.specialization(), command.shift());
            }

            employeeRepository.save(existingEmployee);
        } else {
            Nurse newNurse = Nurse.create(
                    user,
                    command.admissionDate(),
                    command.coren(),
                    command.specialization(),
                    command.shift()
            );
            employeeRepository.save(newNurse);
        }

        userRepository.save(user);
    }
}