package org.seniorcare.identityaccess.application.commands.handlers.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.seniorcare.identityaccess.application.commands.impl.user.CreateUserCommand;
import org.seniorcare.identityaccess.domain.entities.Role;
import org.seniorcare.identityaccess.domain.entities.User;
import org.seniorcare.identityaccess.domain.repositories.IRoleRepository;
import org.seniorcare.identityaccess.domain.repositories.IUserRepository;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserCommandHandlerTest {

    @Mock
    private IUserRepository userRepository;

    @Mock
    private IRoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private CreateUserCommandHandler handler;

    private Role defaultRole;

    @BeforeEach
    void setUp() {
        defaultRole = new Role(UUID.randomUUID(), "DEFAULT_USER", new HashSet<>());
    }

    @Test
    void handle_withValidCommand_shouldCreateAndSaveUser() {
        CreateUserCommand command = new CreateUserCommand(
                "Maria Souza", "maria@example.com", "11999999999", null, "Senha@123");

        when(userRepository.findByEmail("maria@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("DEFAULT_USER")).thenReturn(Optional.of(defaultRole));
        when(passwordEncoder.encode(anyString())).thenReturn("$2a$10$hashed");

        UUID result = handler.handle(command);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void handle_withDuplicateEmail_shouldThrowIllegalState() {
        CreateUserCommand command = new CreateUserCommand(
                "Maria Souza", "maria@example.com", "11999999999", null, "Senha@123");

        User existingUser = User.create("Outro", "maria@example.com", "11888888888", null,
                new org.seniorcare.identityaccess.domain.vo.HashedPassword("$2a$10$hashed"), UUID.randomUUID());
        when(userRepository.findByEmail("maria@example.com")).thenReturn(Optional.of(existingUser));

        assertThrows(IllegalStateException.class, () -> handler.handle(command));
        verify(userRepository, never()).save(any());
    }

    @Test
    void handle_whenDefaultRoleNotFound_shouldThrowIllegalState() {
        CreateUserCommand command = new CreateUserCommand(
                "Maria Souza", "maria@example.com", "11999999999", null, "Senha@123");

        when(userRepository.findByEmail("maria@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("DEFAULT_USER")).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> handler.handle(command));
        verify(userRepository, never()).save(any());
    }

    @Test
    void handle_withInvalidPasswordFormat_shouldThrowBadRequest() {
        CreateUserCommand command = new CreateUserCommand(
                "Maria Souza", "maria@example.com", "11999999999", null, "fraca");

        when(userRepository.findByEmail("maria@example.com")).thenReturn(Optional.empty());
        when(roleRepository.findByName("DEFAULT_USER")).thenReturn(Optional.of(defaultRole));

        assertThrows(BadRequestException.class, () -> handler.handle(command));
        verify(userRepository, never()).save(any());
    }
}
