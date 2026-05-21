package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.ChangePasswordCommand;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.domain.vo.HashedPassword;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChangePasswordCommandHandler {

    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public ChangePasswordCommandHandler(IUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void handle(ChangePasswordCommand command) {
        User user = userRepository.findById(command.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + command.userId()));

        HashedPassword hashed = new HashedPassword(passwordEncoder.encode(command.newPassword()));
        user.changePassword(hashed);
        user.markPasswordChanged();
        userRepository.save(user);
    }
}
