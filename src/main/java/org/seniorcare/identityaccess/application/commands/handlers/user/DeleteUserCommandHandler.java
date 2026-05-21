package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.DeleteUserCommand;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeleteUserCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;

    public DeleteUserCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public void handle(DeleteUserCommand command) {
        User userToDelete = userRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + command.id() + " not found."));

        Role defaultRole = roleRepository.findByName("DEFAULT_USER")
                .orElseThrow(() -> new ResourceNotFoundException("Role 'DEFAULT_USER' not found."));

        userToDelete.deactivate();
        userToDelete.changeRole(defaultRole.getId());

        userRepository.save(userToDelete);
    }
}
