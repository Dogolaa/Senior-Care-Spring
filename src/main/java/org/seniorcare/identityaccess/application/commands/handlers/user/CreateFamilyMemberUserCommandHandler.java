package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.seniorcare.identityaccess.application.commands.impl.user.CreateFamilyMemberUserCommand;
import org.seniorcare.identityaccess.application.ports.output.IEmailNotificationPort;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.identityaccess.domain.vo.HashedPassword;
import org.seniorcare.identityaccess.domain.vo.Password;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.UUID;

@Service
public class CreateFamilyMemberUserCommandHandler {

    private static final String TEMP_PASSWORD_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int TEMP_PASSWORD_LENGTH = 8;
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    private final IUserRepository userRepository;
    private final IRoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final IEmailNotificationPort emailNotificationPort;

    public CreateFamilyMemberUserCommandHandler(
            IUserRepository userRepository,
            IRoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            IEmailNotificationPort emailNotificationPort) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailNotificationPort = emailNotificationPort;
    }

    @Transactional
    public UUID handle(CreateFamilyMemberUserCommand command) {
        if (userRepository.findByEmail(command.email()).isPresent()) {
            throw new IllegalStateException("Já existe uma conta com o e-mail: " + command.email());
        }

        Role familyMemberRole = roleRepository.findByName("FAMILY_MEMBER")
                .orElseThrow(() -> new IllegalStateException("FATAL: Role 'FAMILY_MEMBER' não encontrada no banco de dados."));

        String temporaryPassword = generateTemporaryPassword();

        Password plainPasswordVO = new Password(temporaryPassword);
        HashedPassword hashedPasswordVO = new HashedPassword(passwordEncoder.encode(plainPasswordVO.value()));

        User newUser = User.create(
                command.name(),
                command.email(),
                command.phone(),
                null,
                hashedPasswordVO,
                familyMemberRole.getId()
        );

        userRepository.save(newUser);

        emailNotificationPort.sendFamilyMemberWelcomeEmail(command.email(), command.name(), temporaryPassword);

        return newUser.getId();
    }

    private String generateTemporaryPassword() {
        StringBuilder sb = new StringBuilder(TEMP_PASSWORD_LENGTH + 4);
        // Prefixo fixo garante ao menos 1 maiúscula, 1 minúscula, 1 dígito e 1 especial
        sb.append("Sc@1");
        for (int i = 0; i < TEMP_PASSWORD_LENGTH; i++) {
            sb.append(TEMP_PASSWORD_CHARS.charAt(SECURE_RANDOM.nextInt(TEMP_PASSWORD_CHARS.length())));
        }
        return sb.toString();
    }
}
