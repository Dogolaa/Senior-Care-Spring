package org.seniorcare.identityaccess.application.commands.handlers;

import org.seniorcare.identityaccess.application.commands.impl.CreateUserCommand;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CreateUserCommandHandler {

    private final IUserRepository userRepository;

    public CreateUserCommandHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void handle(CreateUserCommand command) {

        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new IllegalStateException("An account with this email already exists.");
        }

        User newUser = User.create(
                command.name(),
                command.email(),
                command.phone(),
                command.addressId(),
                command.plainTextPassword(),
                command.roleId()
        );
        
        userRepository.save(newUser);
    }

}
