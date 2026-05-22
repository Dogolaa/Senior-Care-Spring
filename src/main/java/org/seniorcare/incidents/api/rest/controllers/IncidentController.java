package org.seniorcare.incidents.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.incidents.api.rest.dto.CreateIncidentRequest;
import org.seniorcare.incidents.api.rest.dto.UpdateIncidentRequest;
import org.seniorcare.incidents.application.commands.handlers.CreateIncidentCommandHandler;
import org.seniorcare.incidents.application.commands.handlers.DeleteIncidentCommandHandler;
import org.seniorcare.incidents.application.commands.handlers.UpdateIncidentCommandHandler;
import org.seniorcare.incidents.application.commands.impl.CreateIncidentCommand;
import org.seniorcare.incidents.application.commands.impl.DeleteIncidentCommand;
import org.seniorcare.incidents.application.commands.impl.UpdateIncidentCommand;
import org.seniorcare.incidents.application.queries.dto.IncidentResponse;
import org.seniorcare.incidents.application.queries.handlers.FindIncidentsByResidentIdQueryHandler;
import org.seniorcare.incidents.application.queries.impl.FindIncidentsByResidentIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/incidents")
@Tag(name = "Incidents", description = "Endpoints para Registro de Incidentes")
public class IncidentController {

    private final CreateIncidentCommandHandler createHandler;
    private final UpdateIncidentCommandHandler updateHandler;
    private final DeleteIncidentCommandHandler deleteHandler;
    private final FindIncidentsByResidentIdQueryHandler findByResidentHandler;

    public IncidentController(
            CreateIncidentCommandHandler createHandler,
            UpdateIncidentCommandHandler updateHandler,
            DeleteIncidentCommandHandler deleteHandler,
            FindIncidentsByResidentIdQueryHandler findByResidentHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.findByResidentHandler = findByResidentHandler;
    }

    @Operation(summary = "Registra um novo incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Incidente registrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_INCIDENTS')")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateIncidentRequest request) {
        var command = new CreateIncidentCommand(
                request.getResidentId(),
                request.getReportedById(),
                request.getIncidentType(),
                request.getSeverity(),
                request.getDescription(),
                request.getActionTaken(),
                request.getOccurredAt(),
                request.getRoom()
        );
        UUID newId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualiza um incidente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incidente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_INCIDENTS')")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateIncidentRequest request) {
        var command = new UpdateIncidentCommand(
                id,
                request.getIncidentType(),
                request.getSeverity(),
                request.getDescription(),
                request.getActionTaken(),
                request.getOccurredAt(),
                request.getRoom()
        );
        updateHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um incidente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Incidente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Incidente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_INCIDENTS')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteHandler.handle(new DeleteIncidentCommand(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista os incidentes de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAuthority('MANAGE_INCIDENTS') or hasAuthority('VIEW_INCIDENTS')")
    public ResponseEntity<List<IncidentResponse>> findByResident(@PathVariable UUID residentId) {
        var query = new FindIncidentsByResidentIdQuery(residentId);
        return ResponseEntity.ok(findByResidentHandler.handle(query));
    }
}
