package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToManagerCommand;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Manager;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.IManagerRepository;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PromoteUserToManagerCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;
    private final IManagerRepository managerRepository;

    public PromoteUserToManagerCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository, IEmployeeRepository employeeRepository, IManagerRepository managerRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
        this.managerRepository = managerRepository;
    }

    @Transactional
    public void handle(PromoteUserToManagerCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User to be promoted not found with id: " + command.userId()));

        Optional<Employee> existingEmployeeOpt = employeeRepository.findByUserIdIncludingDeleted(user.getId());


        Role managerRole = roleRepository.findByName("MANAGER")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'MANAGER' not found. Please seed the database."));

        user.changeRole(managerRole.getId());

        if (existingEmployeeOpt.isPresent()) {
            Employee existingEmployee = existingEmployeeOpt.get();
            if (existingEmployee.getDeletedAt() == null) {
                throw new BadRequestException("User with id " + user.getId() + " is already an active employee.");
            }

            existingEmployee.reactivate();

            if (existingEmployee instanceof Manager managerToUpdate) {
                managerToUpdate.updateDetails(command.department(), command.shift());
            }

            employeeRepository.save(existingEmployee);
        } else {
            Manager newManager = Manager.create(
                    user,
                    command.admissionDate(),
                    command.department(),
                    command.shift()
            );
            employeeRepository.save(newManager);
        }

        userRepository.save(user);
    }
}