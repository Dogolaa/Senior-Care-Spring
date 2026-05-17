package org.seniorcare.health.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.health.api.rest.dto.CreateHealthRecordRequest;
import org.seniorcare.health.api.rest.dto.UpdateHealthRecordRequest;
import org.seniorcare.health.api.rest.dto.push_vitals.PushVitalsRequest;
import org.seniorcare.health.application.commands.handlers.AddPhotoToHealthRecordHistoryCommandHandler;
import org.seniorcare.health.application.commands.handlers.CreateHealthRecordCommandHandler;
import org.seniorcare.health.application.commands.handlers.PushVitalsCommandHandler;
import org.seniorcare.health.application.commands.handlers.UpdateHealthRecordCommandHandler;
import org.seniorcare.health.application.commands.impl.AddPhotoToHealthRecordHistoryCommand;
import org.seniorcare.health.application.commands.impl.CreateHealthRecordCommand;
import org.seniorcare.health.application.commands.impl.PushVitalsCommand;
import org.seniorcare.health.application.commands.impl.UpdateHealthRecordCommand;
import org.seniorcare.health.application.queries.dto.HealthRecordResponse;
import org.seniorcare.health.application.queries.handlers.FindHealthRecordByResidentIdQueryHandler;
import org.seniorcare.health.application.queries.impl.FindHealthRecordByResidentIdQuery;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/health-records")
@Tag(name = "Health Records", description = "Endpoints para Gerenciamento de Prontuários Médicos")
public class HealthRecordController {

    private final CreateHealthRecordCommandHandler createHealthRecordHandler;
    private final UpdateHealthRecordCommandHandler updateHealthRecordHandler;
    private final FindHealthRecordByResidentIdQueryHandler findHealthRecordHandler;
    private final PushVitalsCommandHandler pushVitalsHandler;
    private final AddPhotoToHealthRecordHistoryCommandHandler addPhotoHandler;

    public HealthRecordController(
            CreateHealthRecordCommandHandler createHealthRecordHandler,
            UpdateHealthRecordCommandHandler updateHealthRecordHandler,
            FindHealthRecordByResidentIdQueryHandler findHealthRecordHandler,
            PushVitalsCommandHandler pushVitalsHandler,
            AddPhotoToHealthRecordHistoryCommandHandler addPhotoHandler) {
        this.createHealthRecordHandler = createHealthRecordHandler;
        this.updateHealthRecordHandler = updateHealthRecordHandler;
        this.findHealthRecordHandler = findHealthRecordHandler;
        this.pushVitalsHandler = pushVitalsHandler;
        this.addPhotoHandler = addPhotoHandler;
    }

    @Operation(summary = "Cria um novo prontuário médico para um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prontuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou prontuário já existente para o residente"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado a realizar esta operação")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> createHealthRecord(@Valid @RequestBody CreateHealthRecordRequest request) {
        var command = new CreateHealthRecordCommand(
                request.getResidentId(),
                request.getUpdatedById(),
                request.getHeight(),
                request.getWeight(),
                request.getBloodPressure(),
                request.getHeartRate(),
                request.getTemperature(),
                request.getSaturation()
        );

        final UUID newHealthRecordId = createHealthRecordHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/health-records/{id}")
                .buildAndExpand(newHealthRecordId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualiza um prontuário médico existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado a realizar esta operação")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> updateHealthRecord(@PathVariable UUID id, @Valid @RequestBody UpdateHealthRecordRequest request) {
        var command = new UpdateHealthRecordCommand(
                id,
                request.getUpdatedById(),
                request.getHeight(),
                request.getWeight(),
                request.getBloodPressure(),
                request.getHeartRate(),
                request.getTemperature(),
                request.getSaturation()
        );

        updateHealthRecordHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Busca o prontuário médico de um residente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Prontuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS') or hasAuthority('VIEW_RESIDENT_RECORDS')")
    public ResponseEntity<HealthRecordResponse> findByResident(@PathVariable UUID residentId) {
        var query = new FindHealthRecordByResidentIdQuery(residentId);
        HealthRecordResponse response = findHealthRecordHandler.handle(query);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Recebe sinais vitais enviados automaticamente por wearable ou dispositivo médico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sinais vitais registrados com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Prontuário não encontrado para o residente"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/vitals/push")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> pushVitals(@Valid @RequestBody PushVitalsRequest request) {
        var command = new PushVitalsCommand(
                request.getResidentId(),
                request.getRecordedById(),
                request.getHeartRate(),
                request.getSaturation(),
                request.getBloodPressure(),
                request.getTemperature(),
                request.getSource()
        );
        pushVitalsHandler.handle(command);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Adiciona uma foto a uma medição do prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto adicionada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Medição não encontrada"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping(value = "/histories/{historyId}/photos", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> addPhoto(@PathVariable UUID historyId,
                                          @RequestParam("file") MultipartFile file) throws IOException {
        var command = new AddPhotoToHealthRecordHistoryCommand(
                historyId,
                file.getBytes(),
                file.getOriginalFilename(),
                file.getContentType()
        );
        addPhotoHandler.handle(command);
        return ResponseEntity.ok().build();
    }
}
