package org.seniorcare.health.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.health.api.rest.dto.prescription.CreatePrescriptionRequest;
import org.seniorcare.health.application.commands.handlers.CreatePrescriptionCommandHandler;
import org.seniorcare.health.application.commands.impl.CreatePrescriptionCommand;
import org.seniorcare.health.application.queries.dto.PrescriptionResponse;
import org.seniorcare.health.application.queries.handlers.FindPrescriptionsByHealthRecordIdQueryHandler;
import org.seniorcare.health.application.queries.impl.FindPrescriptionsByHealthRecordIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/prescriptions")
@Tag(name = "Prescriptions", description = "Endpoints para gerenciamento de prescrições médicas")
public class PrescriptionController {

    private final CreatePrescriptionCommandHandler createPrescriptionCommandHandler;
    private final FindPrescriptionsByHealthRecordIdQueryHandler findPrescriptionsQueryHandler;

    public PrescriptionController(CreatePrescriptionCommandHandler createPrescriptionCommandHandler,
                                   FindPrescriptionsByHealthRecordIdQueryHandler findPrescriptionsQueryHandler) {
        this.createPrescriptionCommandHandler = createPrescriptionCommandHandler;
        this.findPrescriptionsQueryHandler = findPrescriptionsQueryHandler;
    }

    @Operation(summary = "Cria uma nova prescrição médica vinculada a um prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Prescrição criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Medicamento não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS')")
    public ResponseEntity<Void> createPrescription(@Valid @RequestBody CreatePrescriptionRequest request) {
        var command = new CreatePrescriptionCommand(
                request.getMedicalRecordId(),
                request.getMedicationId(),
                request.getDosage(),
                request.getStartDate(),
                request.getEndDate()
        );

        UUID prescriptionId = createPrescriptionCommandHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(prescriptionId)
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Lista todas as prescrições de um prontuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de prescrições retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/health-record/{healthRecordId}")
    @PreAuthorize("hasAuthority('MANAGE_HEALTH_RECORDS') or hasAuthority('VIEW_RESIDENT_RECORDS')")
    public ResponseEntity<List<PrescriptionResponse>> findByHealthRecord(@PathVariable UUID healthRecordId) {
        var query = new FindPrescriptionsByHealthRecordIdQuery(healthRecordId);
        List<PrescriptionResponse> prescriptions = findPrescriptionsQueryHandler.handle(query);
        return ResponseEntity.ok(prescriptions);
    }
}
