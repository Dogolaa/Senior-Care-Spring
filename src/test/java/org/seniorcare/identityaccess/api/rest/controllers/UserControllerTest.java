package org.seniorcare.identityaccess.api.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.seniorcare.config.SecurityConfig;
import org.seniorcare.identityaccess.application.commands.handlers.user.DeleteUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.user.UpdateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.user.UpdateUserPhotoCommandHandler;
import org.seniorcare.identityaccess.application.dto.user.UserDTO;
import org.seniorcare.identityaccess.application.queries.handlers.user.FindAllUsersQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.user.FindUserByIdQueryHandler;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UpdateUserCommandHandler updateHandler;

    @MockBean
    private DeleteUserCommandHandler deleteHandler;

    @MockBean
    private UpdateUserPhotoCommandHandler updatePhotoHandler;

    @MockBean
    private FindUserByIdQueryHandler findByIdHandler;

    @MockBean
    private FindAllUsersQueryHandler findAllHandler;

    @MockBean
    private org.seniorcare.identityaccess.infrastructure.security.JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    private UserDTO buildUserDTO(UUID id) {
        return new UserDTO(id, "João Silva", "joao@example.com", "11999999999",
                true, "USER", null, Instant.now(), Instant.now());
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void findUserById_withExistingUser_shouldReturn200() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = buildUserDTO(userId);

        when(findByIdHandler.handle(any())).thenReturn(Optional.of(userDTO));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.name").value("João Silva"));
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void findUserById_whenNotFound_shouldReturn404() throws Exception {
        UUID userId = UUID.randomUUID();

        when(findByIdHandler.handle(any())).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isNotFound());
    }

    @Test
    void findUserById_unauthenticated_shouldReturn401Or403() throws Exception {
        mockMvc.perform(get("/api/v1/users/{id}", UUID.randomUUID()))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void findUserById_withWrongAuthority_shouldReturn403() throws Exception {
        UUID userId = UUID.randomUUID();
        when(findByIdHandler.handle(any())).thenReturn(Optional.of(buildUserDTO(userId)));

        mockMvc.perform(get("/api/v1/users/{id}", userId))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void findAllUsers_shouldReturn200WithPage() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDTO userDTO = buildUserDTO(userId);

        when(findAllHandler.handle(any())).thenReturn(
                new PageImpl<>(List.of(userDTO), PageRequest.of(0, 10), 1));

        mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "UPDATE_USER")
    void updateUser_withValidRequest_shouldReturn200() throws Exception {
        UUID userId = UUID.randomUUID();
        UserDTO updatedDTO = buildUserDTO(userId);

        when(updateHandler.handle(any())).thenReturn(updatedDTO);

        String requestBody = """
                {
                    "name": "João Atualizado",
                    "email": "joao.atualizado@example.com",
                    "phone": "11888888888",
                    "roleId": "%s"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId.toString()));
    }

    @Test
    @WithMockUser(authorities = "UPDATE_USER")
    void updateUser_whenNotFound_shouldReturn404() throws Exception {
        UUID userId = UUID.randomUUID();

        when(updateHandler.handle(any())).thenThrow(new ResourceNotFoundException("User not found"));

        String requestBody = """
                {
                    "name": "João",
                    "email": "joao@example.com",
                    "roleId": "%s"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(put("/api/v1/users/{id}", userId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    void deleteUser_withExistingUser_shouldReturn204() throws Exception {
        UUID userId = UUID.randomUUID();
        doNothing().when(deleteHandler).handle(any());

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .with(csrf()))
                .andExpect(status().isNoContent());

        verify(deleteHandler).handle(any());
    }

    @Test
    @WithMockUser(authorities = "DELETE_USER")
    void deleteUser_whenNotFound_shouldReturn404() throws Exception {
        UUID userId = UUID.randomUUID();
        doThrow(new ResourceNotFoundException("User not found")).when(deleteHandler).handle(any());

        mockMvc.perform(delete("/api/v1/users/{id}", userId)
                        .with(csrf()))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void deleteUser_withWrongAuthority_shouldReturn403() throws Exception {
        mockMvc.perform(delete("/api/v1/users/{id}", UUID.randomUUID())
                        .with(csrf()))
                .andExpect(status().isForbidden());
    }
}
