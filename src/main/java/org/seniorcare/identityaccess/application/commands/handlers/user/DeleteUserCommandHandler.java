package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.DeleteUserCommand;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class DeleteUserCommandHandler {

    private final IUserRepository userRepository;

    public DeleteUserCommandHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void handle(DeleteUserCommand command) {

        User userToDelete = userRepository.findById(command.id())
                .orElseThrow(() -> new NoSuchElementException("User with id " + command.id() + " not found."));

        userToDelete.softDelete();

        userRepository.save(userToDelete);
    }

}
