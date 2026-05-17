package org.seniorcare.health.api.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.seniorcare.config.SecurityConfig;
import org.seniorcare.health.application.commands.handlers.AddPhotoToActivityHistoryCommandHandler;
import org.seniorcare.health.application.commands.handlers.CreateActivityRecordCommandHandler;
import org.seniorcare.health.application.commands.handlers.LogActivityCommandHandler;
import org.seniorcare.health.application.queries.dto.ActivityRecordHistoryResponse;
import org.seniorcare.health.application.queries.dto.ActivityRecordResponse;
import org.seniorcare.health.application.queries.handlers.FindActivityRecordByResidentIdQueryHandler;
import org.seniorcare.shared.exceptions.BadRequestException;
import org.seniorcare.shared.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ActivityRecordController.class)
@Import(SecurityConfig.class)
class ActivityRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateActivityRecordCommandHandler createHandler;

    @MockBean
    private LogActivityCommandHandler logActivityHandler;

    @MockBean
    private AddPhotoToActivityHistoryCommandHandler addPhotoHandler;

    @MockBean
    private FindActivityRecordByResidentIdQueryHandler findByResidentHandler;

    @MockBean
    private org.seniorcare.identityaccess.infrastructure.security.JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void createActivityRecord_withValidRequest_shouldReturn201() throws Exception {
        UUID residentId = UUID.randomUUID();
        UUID staffId = UUID.randomUUID();
        UUID newId = UUID.randomUUID();

        when(createHandler.handle(any())).thenReturn(newId);

        String requestBody = """
                {
                    "residentId": "%s",
                    "conductedById": "%s"
                }
                """.formatted(residentId, staffId);

        mockMvc.perform(post("/api/v1/activity-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(createHandler).handle(any());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void logActivity_withValidRequest_shouldReturn201() throws Exception {
        UUID recordId = UUID.randomUUID();
        UUID historyId = UUID.randomUUID();

        when(logActivityHandler.handle(any())).thenReturn(historyId);

        String requestBody = """
                {
                    "activityName": "Clube de Leitura",
                    "description": "Sessão semanal de leitura",
                    "startDateTime": "2025-05-01T10:00:00",
                    "endDateTime": "2025-05-01T11:00:00",
                    "conductedById": "%s"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/v1/activity-records/{id}/activities", recordId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(logActivityHandler).handle(any());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void logActivity_whenRecordNotFound_shouldReturn404() throws Exception {
        UUID recordId = UUID.randomUUID();

        when(logActivityHandler.handle(any())).thenThrow(new ResourceNotFoundException("Not found"));

        String requestBody = """
                {
                    "activityName": "Tomar Sol",
                    "startDateTime": "2025-05-01T10:00:00",
                    "conductedById": "%s"
                }
                """.formatted(UUID.randomUUID());

        mockMvc.perform(post("/api/v1/activity-records/{id}/activities", recordId)
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void findByResident_withExistingRecord_shouldReturn200() throws Exception {
        UUID residentId = UUID.randomUUID();

        ActivityRecordHistoryResponse history = new ActivityRecordHistoryResponse(
                UUID.randomUUID(), "Tomar Sol", "Jardim", LocalDateTime.now(),
                LocalDateTime.now().plusHours(1), UUID.randomUUID(), null,
                LocalDate.now(), List.of());

        ActivityRecordResponse response = new ActivityRecordResponse(
                UUID.randomUUID(), residentId, UUID.randomUUID(), LocalDate.now(), List.of(history));

        when(findByResidentHandler.handle(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/activity-records/resident/{residentId}", residentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residentId").value(residentId.toString()))
                .andExpect(jsonPath("$.history").isArray())
                .andExpect(jsonPath("$.history[0].activityName").value("Tomar Sol"));
    }

    @Test
    @WithMockUser(authorities = "VIEW_RESIDENT_RECORDS")
    void findByResident_withFamilyMemberAuthority_shouldReturn200() throws Exception {
        UUID residentId = UUID.randomUUID();

        ActivityRecordResponse response = new ActivityRecordResponse(
                UUID.randomUUID(), residentId, UUID.randomUUID(), null, List.of());

        when(findByResidentHandler.handle(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/activity-records/resident/{residentId}", residentId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void findByResident_whenNotFound_shouldReturn404() throws Exception {
        UUID residentId = UUID.randomUUID();
        when(findByResidentHandler.handle(any())).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/activity-records/resident/{residentId}", residentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void findByResident_withWrongAuthority_shouldReturn403() throws Exception {
        mockMvc.perform(get("/api/v1/activity-records/resident/{residentId}", UUID.randomUUID()))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_ACTIVITIES")
    void addPhoto_withValidFile_shouldReturn200() throws Exception {
        UUID historyId = UUID.randomUUID();
        doNothing().when(addPhotoHandler).handle(any());

        MockMultipartFile file = new MockMultipartFile(
                "file", "foto.jpg", "image/jpeg", "fake image content".getBytes());

        mockMvc.perform(multipart("/api/v1/activity-records/histories/{historyId}/photos", historyId)
                        .file(file)
                        .with(csrf()))
                .andExpect(status().isOk());

        verify(addPhotoHandler).handle(any());
    }
}
