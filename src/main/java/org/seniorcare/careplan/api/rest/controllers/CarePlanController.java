package org.seniorcare.careplan.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.careplan.api.rest.dto.CreateCarePlanRequest;
import org.seniorcare.careplan.api.rest.dto.UpdateCarePlanRequest;
import org.seniorcare.careplan.application.commands.handlers.CreateCarePlanCommandHandler;
import org.seniorcare.careplan.application.commands.handlers.DeleteCarePlanCommandHandler;
import org.seniorcare.careplan.application.commands.handlers.UpdateCarePlanCommandHandler;
import org.seniorcare.careplan.application.commands.impl.CreateCarePlanCommand;
import org.seniorcare.careplan.application.commands.impl.DeleteCarePlanCommand;
import org.seniorcare.careplan.application.commands.impl.UpdateCarePlanCommand;
import org.seniorcare.careplan.application.queries.dto.CarePlanResponse;
import org.seniorcare.careplan.application.queries.handlers.FindCarePlansByResidentIdQueryHandler;
import org.seniorcare.careplan.application.queries.impl.FindCarePlansByResidentIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/care-plans")
@Tag(name = "Care Plans", description = "Endpoints para Planos de Cuidado dos Residentes")
public class CarePlanController {

    private final CreateCarePlanCommandHandler createHandler;
    private final UpdateCarePlanCommandHandler updateHandler;
    private final DeleteCarePlanCommandHandler deleteHandler;
    private final FindCarePlansByResidentIdQueryHandler findByResidentHandler;

    public CarePlanController(
            CreateCarePlanCommandHandler createHandler,
            UpdateCarePlanCommandHandler updateHandler,
            DeleteCarePlanCommandHandler deleteHandler,
            FindCarePlansByResidentIdQueryHandler findByResidentHandler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.findByResidentHandler = findByResidentHandler;
    }

    @Operation(summary = "Cria um plano de cuidado para um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Plano criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('MANAGE_CARE_PLANS')")
    public ResponseEntity<Void> create(@Valid @RequestBody CreateCarePlanRequest request) {
        var command = new CreateCarePlanCommand(
                request.getResidentId(),
                request.getResponsibleId(),
                request.getTitle(),
                request.getDescription(),
                request.getGoals(),
                request.getInterventions(),
                request.getStartDate(),
                request.getEndDate()
        );
        UUID newId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualiza um plano de cuidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_CARE_PLANS')")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateCarePlanRequest request) {
        var command = new UpdateCarePlanCommand(
                id,
                request.getTitle(),
                request.getDescription(),
                request.getGoals(),
                request.getInterventions(),
                request.getStartDate(),
                request.getEndDate(),
                request.getStatus()
        );
        updateHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove um plano de cuidado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Plano removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Plano não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_CARE_PLANS')")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        deleteHandler.handle(new DeleteCarePlanCommand(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Lista os planos de cuidado de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/resident/{residentId}")
    @PreAuthorize("hasAuthority('MANAGE_CARE_PLANS') or hasAuthority('VIEW_CARE_PLANS')")
    public ResponseEntity<List<CarePlanResponse>> findByResident(@PathVariable UUID residentId) {
        return ResponseEntity.ok(findByResidentHandler.handle(new FindCarePlansByResidentIdQuery(residentId)));
    }
}
