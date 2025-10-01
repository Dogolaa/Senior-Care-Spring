package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.CreateUserCommand;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class CreateUserCommandHandler {

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserCommandHandler(IUserRepository userRepository, IRoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public UUID handle(CreateUserCommand command) {
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new IllegalStateException("An account with this email already exists.");
        }

        Role defaultRole = roleRepository.findByName("DEFAULT_USER")
                .orElseThrow(() -> new IllegalStateException("FATAL: Default role 'DEFAULT_USER' not found in database."));

        String hashedPassword = passwordEncoder.encode(command.plainTextPassword());

        User newUser = User.create(
                command.name(),
                command.email(),
                command.phone(),
                command.addressId(),
                hashedPassword,
                defaultRole.getId()
        );

        userRepository.save(newUser);
        return newUser.getId();
    }
}