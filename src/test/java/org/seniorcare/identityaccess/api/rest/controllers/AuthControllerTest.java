package org.seniorcare.identityaccess.api.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.seniorcare.config.SecurityConfig;
import org.seniorcare.identityaccess.application.commands.handlers.user.CreateUserCommandHandler;
import org.seniorcare.identityaccess.application.services.AuthenticationService;
import org.seniorcare.identityaccess.api.rest.dto.auth.LoginResponse;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@Import(SecurityConfig.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateUserCommandHandler createHandler;

    @MockBean
    private AuthenticationService authenticationService;

    @MockBean
    private org.seniorcare.identityaccess.infrastructure.security.JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    void register_withValidRequest_shouldReturn201() throws Exception {
        UUID newUserId = UUID.randomUUID();
        when(createHandler.handle(any())).thenReturn(newUserId);

        String requestBody = """
                {
                    "name": "Maria Souza",
                    "email": "maria@example.com",
                    "phone": "11999999999",
                    "password": "Senha@123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(createHandler).handle(any());
    }

    @Test
    void register_withDuplicateEmail_shouldReturn500() throws Exception {
        when(createHandler.handle(any())).thenThrow(new IllegalStateException("Email already in use"));

        String requestBody = """
                {
                    "name": "Maria Souza",
                    "email": "existente@example.com",
                    "phone": "11999999999",
                    "password": "Senha@123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }

    @Test
    void register_withInvalidPassword_shouldReturn400() throws Exception {
        when(createHandler.handle(any())).thenThrow(new BadRequestException("Password too weak"));

        String requestBody = """
                {
                    "name": "Maria Souza",
                    "email": "maria@example.com",
                    "phone": "11999999999",
                    "password": "fraca"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/register")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    void login_withValidCredentials_shouldReturn200WithToken() throws Exception {
        LoginResponse loginResponse = new LoginResponse(
                "jwt.token.here", UUID.randomUUID(), "Maria", "maria@example.com", "DEFAULT_USER");
        when(authenticationService.login(any())).thenReturn(loginResponse);

        String requestBody = """
                {
                    "email": "maria@example.com",
                    "password": "Senha@123"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("jwt.token.here"));
    }

    @Test
    void login_withInvalidCredentials_shouldReturn500() throws Exception {
        when(authenticationService.login(any())).thenThrow(new RuntimeException("Bad credentials"));

        String requestBody = """
                {
                    "email": "maria@example.com",
                    "password": "SenhaErrada@1"
                }
                """;

        mockMvc.perform(post("/api/v1/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isInternalServerError());
    }
}
