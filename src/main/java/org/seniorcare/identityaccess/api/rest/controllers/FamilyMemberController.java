package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.identityaccess.api.rest.dto.user.CreateFamilyMemberUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.user.CreateFamilyMemberUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.user.CreateFamilyMemberUserCommand;
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
@RequestMapping("/api/v1/family-members")
@Tag(name = "Family Member Management", description = "Endpoints para Gerenciamento de Contas de Familiares")
public class FamilyMemberController {

    private final CreateFamilyMemberUserCommandHandler createHandler;

    public FamilyMemberController(CreateFamilyMemberUserCommandHandler createHandler) {
        this.createHandler = createHandler;
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
}
