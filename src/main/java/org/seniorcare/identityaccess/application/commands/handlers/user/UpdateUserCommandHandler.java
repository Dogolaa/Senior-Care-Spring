package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.UpdateUserCommand;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateUserCommandHandler {

    private final IUserRepository userRepository;

    public UpdateUserCommandHandler(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UUID handle(UpdateUserCommand command) {

        User userToUpdate = userRepository.findById(command.id())
                .orElseThrow(() -> new ResourceNotFoundException("User with id " + command.id() + " not found."));

        Optional<User> userWithSameEmail = userRepository.findByEmail(command.email());

        if (userWithSameEmail.isPresent() && !userWithSameEmail.get().getId().equals(userToUpdate.getId())) {
            throw new IllegalStateException("Email " + command.email() + " is already in use by another user.");
        }

        userToUpdate.update(
                command.name(),
                command.email(),
                command.phone(),
                command.addressId(),
                command.roleId()
        );

        userRepository.save(userToUpdate);

        return userToUpdate.getId();
    }
}
