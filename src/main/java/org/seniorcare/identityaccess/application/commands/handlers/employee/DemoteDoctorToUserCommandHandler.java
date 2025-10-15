package org.seniorcare.identityaccess.application.commands.handlers.employee;

import org.seniorcare.identityaccess.application.commands.impl.employee.DemoteDoctorToUserCommand;
import org.seniorcare.identityaccess.domain.entities.Employee;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IEmployeeRepository;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DemoteDoctorToUserCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final IEmployeeRepository employeeRepository;

    public DemoteDoctorToUserCommandHandler(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            IEmployeeRepository employeeRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.employeeRepository = employeeRepository;
    }

    @Transactional
    public void handle(DemoteDoctorToUserCommand command) {
        Employee employee = employeeRepository.findById(command.employeeId())
                .orElseThrow(() -> new ResourceNotFoundException("Employee to be demoted not found with id: " + command.employeeId()));

        employee.softDelete();

        User user = employee.getUser();

        Role defaultRole = roleRepository.findByName("DEFAULT_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'DEFAULT_USER' not found. Please seed the database."));


        user.changeRole(defaultRole.getId());

        userRepository.save(user);
        employeeRepository.save(employee);
    }
}
