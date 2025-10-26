package org.seniorcare.residentmanagement.api.rest.controllers.resident;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.residentmanagement.api.rest.dto.AdmitResidentRequest;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.AdmitResidentCommandHandler;
import org.seniorcare.residentmanagement.application.commands.impl.resident.AdmitResidentCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/residents")
@Tag(name = "Resident Management", description = "Endpoints para Gerenciamento de Residentes")
public class ResidentsController {

    private final AdmitResidentCommandHandler admitResidentHandler;

    public ResidentsController(AdmitResidentCommandHandler admitResidentHandler) {
        this.admitResidentHandler = admitResidentHandler;
    }

    @Operation(summary = "Admite um novo residente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Residente admitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou regras de negócio violadas (ex: CPF duplicado)"),
            @ApiResponse(responseCode = "404", description = "Usuário responsável não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado a realizar esta operação")
    })
    @PostMapping("/admit")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> admitResident(@Valid @RequestBody AdmitResidentRequest request) {

        var command = new AdmitResidentCommand(
                request.responsibleId(),
                request.name(),
                request.cpf(),
                request.rg(),
                request.dateOfBirth(),
                request.gender(),
                request.bloodType(),
                request.room()
        );

        final UUID newResidentId = admitResidentHandler.handle(command);
        
        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/residents/{id}")
                .buildAndExpand(newResidentId).toUri();

        return ResponseEntity.created(location).build();
    }

    // TODO: Adicionar endpoints de Query (findById, findAll)
    // TODO: Adicionar endpoints de Command (transferRoom, recordDischarge, addFamilyLink, addAllergy, etc.)
}