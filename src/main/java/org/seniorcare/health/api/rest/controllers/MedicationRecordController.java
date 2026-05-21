package org.seniorcare.health.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.health.api.rest.dto.AddPhotoRequest;
import org.seniorcare.health.api.rest.dto.medication_record.LogMedicationAdministrationRequest;
import org.seniorcare.health.application.commands.handlers.AddPhotoToMedicationRecordCommandHandler;
import org.seniorcare.health.application.commands.handlers.LogMedicationAdministrationCommandHandler;
import org.seniorcare.health.application.commands.impl.AddPhotoToMedicationRecordCommand;
import org.seniorcare.health.application.commands.impl.LogMedicationAdministrationCommand;
import org.seniorcare.health.application.queries.dto.MedicationRecordResponse;
import org.seniorcare.health.application.queries.handlers.FindMedicationRecordsByResidentIdQueryHandler;
import org.seniorcare.health.application.queries.impl.FindMedicationRecordsByResidentIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/medication-records")
@Tag(name = "Medication Records", description = "Endpoints para registro de administração de medicamentos")
public class MedicationRecordController {

    private final LogMedicationAdministrationCommandHandler logMedicationHandler;
    private final FindMedicationRecordsByResidentIdQueryHandler findMedicationRecordsHandler;
    private final AddPhotoToMedicationRecordCommandHandler addPhotoHandler;

    public MedicationRecordController(LogMedicationAdministrationCommandHandler logMedicationHandler,
                                       FindMedicationRecordsByResidentIdQueryHandler findMedicationRecordsHandler,
                                       AddPhotoToMedicationRecordCommandHandler addPhotoHandler) {
        this.logMedicationHandler = logMedicationHandler;
        this.findMedicationRecordsHandler = findMedicationRecordsHandler;
        this.addPhotoHandler = addPhotoHandler;
    }

    @Operation(summary = "Registra a administração de um medicamento a um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Administração registrada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> logAdministration(@Valid @RequestBody LogMedicationAdministrationRequest request) {
        var command = new LogMedicationAdministrationCommand(
                request.getResidentId(),
                request.getMedicationId(),
                request.getAdministrationDate(),
                request.getAdministeredById(),
                request.getDose()
        );

        UUID recordId = logMedicationHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(recordId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Lista o histórico de administração de medicamentos de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Histórico retornado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS') or hasAuthority('VIEW_RESIDENT_RECORDS')")
    public ResponseEntity<List<MedicationRecordResponse>> findByResident(@PathVariable UUID residentId) {
        var query = new FindMedicationRecordsByResidentIdQuery(residentId);
        List<MedicationRecordResponse> records = findMedicationRecordsHandler.handle(query);
        return ResponseEntity.ok(records);
    }

    @Operation(summary = "Adiciona uma foto a um registro de administração de medicamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Foto adicionada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/{id}/photos")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> addPhoto(@PathVariable UUID id,
                                          @Valid @RequestBody AddPhotoRequest request) {
        addPhotoHandler.handle(new AddPhotoToMedicationRecordCommand(id, request.getPhotoUrl()));
        return ResponseEntity.ok().build();
    }
}
