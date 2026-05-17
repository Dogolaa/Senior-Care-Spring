package org.seniorcare.health.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.health.api.rest.dto.AddPhotoRequest;
import org.seniorcare.health.api.rest.dto.activity_record.CreateActivityRecordRequest;
import org.seniorcare.health.api.rest.dto.activity_record.LogActivityRequest;
import org.seniorcare.health.application.commands.handlers.AddPhotoToActivityHistoryCommandHandler;
import org.seniorcare.health.application.commands.handlers.CreateActivityRecordCommandHandler;
import org.seniorcare.health.application.commands.handlers.LogActivityCommandHandler;
import org.seniorcare.health.application.commands.impl.AddPhotoToActivityHistoryCommand;
import org.seniorcare.health.application.commands.impl.CreateActivityRecordCommand;
import org.seniorcare.health.application.commands.impl.LogActivityCommand;
import org.seniorcare.health.application.queries.dto.ActivityRecordResponse;
import org.seniorcare.health.application.queries.handlers.FindActivityRecordByResidentIdQueryHandler;
import org.seniorcare.health.application.queries.impl.FindActivityRecordByResidentIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/activity-records")
@Tag(name = "Activity Records", description = "Endpoints para Registro de Atividades dos Residentes")
public class ActivityRecordController {

    private final CreateActivityRecordCommandHandler createHandler;
    private final LogActivityCommandHandler logActivityHandler;
    private final AddPhotoToActivityHistoryCommandHandler addPhotoHandler;
    private final FindActivityRecordByResidentIdQueryHandler findByResidentHandler;

    public ActivityRecordController(
            CreateActivityRecordCommandHandler createHandler,
            LogActivityCommandHandler logActivityHandler,
            AddPhotoToActivityHistoryCommandHandler addPhotoHandler,
            FindActivityRecordByResidentIdQueryHandler findByResidentHandler) {
        this.createHandler = createHandler;
        this.logActivityHandler = logActivityHandler;
        this.addPhotoHandler = addPhotoHandler;
        this.findByResidentHandler = findByResidentHandler;
    }

    @Operation(summary = "Cria o registro de atividades para um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Registro criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Registro já existente para o residente"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_ACTIVITIES')")
    public ResponseEntity<Void> createActivityRecord(@Valid @RequestBody CreateActivityRecordRequest request) {
        var command = new CreateActivityRecordCommand(request.getResidentId(), request.getConductedById());
        UUID newId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Registra uma nova atividade no histórico do residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Atividade registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Registro de atividades não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/{id}/activities")
    @PreAuthorize("hasAuthority('MANAGE_ACTIVITIES')")
    public ResponseEntity<Void> logActivity(@PathVariable UUID id, @Valid @RequestBody LogActivityRequest request) {
        var command = new LogActivityCommand(
                id,
                request.getActivityName(),
                request.getDescription(),
                request.getStartDateTime(),
                request.getEndDateTime(),
                request.getConductedById(),
                request.getNotes()
        );

        UUID historyId = logActivityHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/activity-records/histories/{historyId}")
                .buildAndExpand(historyId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Busca o histórico de atividades de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAuthority('MANAGE_ACTIVITIES') or hasAuthority('VIEW_RESIDENT_RECORDS')")
    public ResponseEntity<ActivityRecordResponse> findByResident(@PathVariable UUID residentId) {
        var query = new FindActivityRecordByResidentIdQuery(residentId);
        ActivityRecordResponse response = findByResidentHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Adiciona uma foto a uma ocorrência de atividade via URL")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto adicionada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Ocorrência de atividade não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/histories/{historyId}/photos")
    @PreAuthorize("hasAuthority('MANAGE_ACTIVITIES')")
    public ResponseEntity<Void> addPhoto(@PathVariable UUID historyId,
                                          @Valid @RequestBody AddPhotoRequest request) {
        var command = new AddPhotoToActivityHistoryCommand(historyId, request.getPhotoUrl());
        addPhotoHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}
