package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.identityaccess.api.rest.dto.user.CreateFamilyMemberUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.user.CreateFamilyMemberUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.user.CreateFamilyMemberUserCommand;
import org.seniorcare.identityaccess.application.security.AuthenticatedPrincipal;
import org.seniorcare.residentmanagement.application.dto.resident.ResidentDTO;
import org.seniorcare.residentmanagement.application.queries.handlers.resident.FindMyResidentsQueryHandler;
import org.seniorcare.residentmanagement.application.queries.impl.resident.FindMyResidentsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/family-members")
@Tag(name = "Family Member Management", description = "Endpoints para Gerenciamento de Contas de Familiares")
public class FamilyMemberController {

    private final CreateFamilyMemberUserCommandHandler createHandler;
    private final FindMyResidentsQueryHandler findMyResidentsHandler;

    public FamilyMemberController(
            CreateFamilyMemberUserCommandHandler createHandler,
            FindMyResidentsQueryHandler findMyResidentsHandler) {
        this.createHandler = createHandler;
        this.findMyResidentsHandler = findMyResidentsHandler;
    }

    @Operation(
            summary = "Cria uma conta de usuário para um familiar",
            description = "Cria um usuário com role FAMILY_MEMBER, gera uma senha temporária e envia um e-mail de boas-vindas com as credenciais. O familiar deverá trocar a senha no primeiro acesso."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Conta do familiar criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos ou e-mail já cadastrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping
    @PreAuthorize("hasAuthority('CREATE_FAMILY_MEMBER')")
    public ResponseEntity<Void> createFamilyMemberUser(@Valid @RequestBody CreateFamilyMemberUserRequest request) {
        var command = new CreateFamilyMemberUserCommand(
                request.name(),
                request.email(),
                request.phone()
        );

        final UUID newUserId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{id}")
                .buildAndExpand(newUserId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(
            summary = "Lista os residentes vinculados ao familiar autenticado",
            description = "Retorna apenas os residentes aos quais o familiar logado está vinculado via family-link ativo."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/my-residents")
    @PreAuthorize("hasAuthority('VIEW_RESIDENT_RECORDS')")
    public ResponseEntity<List<ResidentDTO>> getMyResidents() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthenticatedPrincipal principal = (AuthenticatedPrincipal) auth.getPrincipal();

        var query = new FindMyResidentsQuery(principal.getId());
        List<ResidentDTO> residents = findMyResidentsHandler.handle(query);

        return ResponseEntity.ok(residents);
    }
}
