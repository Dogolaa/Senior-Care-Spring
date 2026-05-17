package org.seniorcare.health.api.rest.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.seniorcare.config.SecurityConfig;
import org.seniorcare.health.application.commands.handlers.AddPhotoToHealthRecordHistoryCommandHandler;
import org.seniorcare.health.application.commands.handlers.CreateHealthRecordCommandHandler;
import org.seniorcare.health.application.commands.handlers.PushVitalsCommandHandler;
import org.seniorcare.health.application.commands.handlers.UpdateHealthRecordCommandHandler;
import org.seniorcare.health.application.queries.dto.HealthRecordHistoryResponse;
import org.seniorcare.health.application.queries.dto.HealthRecordResponse;
import org.seniorcare.health.application.queries.handlers.FindHealthRecordByResidentIdQueryHandler;
import org.seniorcare.health.domain.vo.VitalSignsSource;
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
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthRecordController.class)
@Import(SecurityConfig.class)
class HealthRecordControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateHealthRecordCommandHandler createHandler;

    @MockBean
    private UpdateHealthRecordCommandHandler updateHandler;

    @MockBean
    private FindHealthRecordByResidentIdQueryHandler findHandler;

    @MockBean
    private PushVitalsCommandHandler pushVitalsHandler;

    @MockBean
    private AddPhotoToHealthRecordHistoryCommandHandler addPhotoHandler;

    @MockBean
    private org.seniorcare.identityaccess.infrastructure.security.JwtService jwtService;

    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void createHealthRecord_withValidRequest_shouldReturn201() throws Exception {
        UUID residentId = UUID.randomUUID();
        UUID staffId = UUID.randomUUID();
        UUID newId = UUID.randomUUID();

        when(createHandler.handle(any())).thenReturn(newId);

        String requestBody = """
                {
                    "residentId": "%s",
                    "updatedById": "%s",
                    "height": 1.75,
                    "weight": 70.0,
                    "bloodPressure": "120/80",
                    "heartRate": 72,
                    "temperature": 36.5,
                    "saturation": 98.0
                }
                """.formatted(residentId, staffId);

        mockMvc.perform(post("/api/v1/health-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated());

        verify(createHandler).handle(any());
    }

    @Test
    void createHealthRecord_unauthenticated_shouldReturn401Or403() throws Exception {
        String requestBody = """
                {
                    "residentId": "%s",
                    "updatedById": "%s"
                }
                """.formatted(UUID.randomUUID(), UUID.randomUUID());

        mockMvc.perform(post("/api/v1/health-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(authorities = "READ_USER")
    void createHealthRecord_withWrongAuthority_shouldReturn403() throws Exception {
        String requestBody = """
                {
                    "residentId": "%s",
                    "updatedById": "%s"
                }
                """.formatted(UUID.randomUUID(), UUID.randomUUID());

        mockMvc.perform(post("/api/v1/health-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void findByResident_withExistingRecord_shouldReturn200() throws Exception {
        UUID residentId = UUID.randomUUID();
        UUID recordId = UUID.randomUUID();

        HealthRecordHistoryResponse history = new HealthRecordHistoryResponse(
                UUID.randomUUID(), 1.75f, 70f, "120/80", 72, 36.5f, 98f,
                70f / (1.75f * 1.75f), LocalDate.now(), VitalSignsSource.MANUAL, List.of());

        HealthRecordResponse response = new HealthRecordResponse(
                recordId, residentId, UUID.randomUUID(), 1.75f, 70f,
                "120/80", 72, 36.5f, 98f, 70f / (1.75f * 1.75f),
                LocalDate.now(), List.of(history));

        when(findHandler.handle(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/health-records/resident/{residentId}", residentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.residentId").value(residentId.toString()))
                .andExpect(jsonPath("$.history").isArray());
    }

    @Test
    @WithMockUser(authorities = "VIEW_RESIDENT_RECORDS")
    void findByResident_withFamilyMemberAuthority_shouldReturn200() throws Exception {
        UUID residentId = UUID.randomUUID();

        HealthRecordResponse response = new HealthRecordResponse(
                UUID.randomUUID(), residentId, UUID.randomUUID(), 1.75f, 70f,
                "120/80", 72, 36.5f, 98f, null, LocalDate.now(), List.of());

        when(findHandler.handle(any())).thenReturn(response);

        mockMvc.perform(get("/api/v1/health-records/resident/{residentId}", residentId))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void findByResident_whenNotFound_shouldReturn404() throws Exception {
        UUID residentId = UUID.randomUUID();
        when(findHandler.handle(any())).thenThrow(new ResourceNotFoundException("Not found"));

        mockMvc.perform(get("/api/v1/health-records/resident/{residentId}", residentId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void pushVitals_withValidRequest_shouldReturn200() throws Exception {
        UUID residentId = UUID.randomUUID();

        String requestBody = """
                {
                    "residentId": "%s",
                    "recordedById": "%s",
                    "heartRate": 80,
                    "saturation": 97.5,
                    "source": "WEARABLE"
                }
                """.formatted(residentId, UUID.randomUUID());

        doNothing().when(pushVitalsHandler).handle(any());

        mockMvc.perform(post("/api/v1/health-records/vitals/push")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void createHealthRecord_whenDuplicate_shouldReturn400() throws Exception {
        UUID residentId = UUID.randomUUID();

        when(createHandler.handle(any())).thenThrow(new BadRequestException("Health record already exists"));

        String requestBody = """
                {
                    "residentId": "%s",
                    "updatedById": "%s"
                }
                """.formatted(residentId, UUID.randomUUID());

        mockMvc.perform(post("/api/v1/health-records")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(authorities = "MANAGE_HEALTH_RECORDS")
    void addPhoto_withValidFile_shouldReturn200() throws Exception {
        UUID historyId = UUID.randomUUID();
        doNothing().when(addPhotoHandler).handle(any());

        MockMultipartFile file = new MockMultipartFile(
                "file", "foto.jpg", "image/jpeg", "fake image content".getBytes());

        mockMvc.perform(multipart("/api/v1/health-records/histories/{historyId}/photos", historyId)
                        .file(file)
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
