package org.seniorcare.residentmanagement.api.rest.controllers.resident;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.residentmanagement.api.rest.dto.AddFamilyLinkRequest;
import org.seniorcare.residentmanagement.api.rest.dto.AdmitResidentRequest;
import org.seniorcare.residentmanagement.application.commands.handlers.familyLink.AddFamilyLinkCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.AdmitResidentCommandHandler;
import org.seniorcare.residentmanagement.application.commands.impl.familyLink.AddFamilyLinkCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.AdmitResidentCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/residents")
@Tag(name = "Resident Management", description = "Endpoints para Gerenciamento de Residentes")
public class ResidentsController {

    private final AdmitResidentCommandHandler admitResidentHandler;
    private final AddFamilyLinkCommandHandler addFamilyLinkHandler;

    public ResidentsController(
            AdmitResidentCommandHandler admitResidentHandler,
            AddFamilyLinkCommandHandler addFamilyLinkHandler
    ) {
        this.admitResidentHandler = admitResidentHandler;
        this.addFamilyLinkHandler = addFamilyLinkHandler;
    }

    @Operation(summary = "Admite um novo residente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Residente admitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou regras de negócio violadas (ex: CPF duplicado)"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado a realizar esta operação")
    })
    @PostMapping("/admit")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> admitResident(@Valid @RequestBody AdmitResidentRequest request) {

        var command = new AdmitResidentCommand(
                request.name(),
                request.cpf(),
                request.rg(),
                request.dateOfBirth(),
                request.gender(),
                request.bloodType(),
                request.initialAllergies(),
                request.room()
        );

        final UUID newResidentId = admitResidentHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/residents/{id}")
                .buildAndExpand(newResidentId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Adiciona um novo vínculo familiar a um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo familiar criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Residente ou Usuário (familiar) não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado a realizar esta operação")
    })
    @PostMapping("/{residentId}/family-links")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> addFamilyLink(
            @PathVariable UUID residentId,
            @Valid @RequestBody AddFamilyLinkRequest request) {

        var command = new AddFamilyLinkCommand(
                residentId,
                request.familyMemberId(),
                request.relationship(),
                request.isPrimaryContact()
        );

        final UUID newFamilyLinkId = addFamilyLinkHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFamilyLinkId).toUri();

        return ResponseEntity.created(location).build();
    }
}