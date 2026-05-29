package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.auth.ChangePasswordRequest;
import org.seniorcare.identityaccess.api.rest.dto.auth.LoginRequest;
import org.seniorcare.identityaccess.api.rest.dto.auth.LoginResponse;
import org.seniorcare.identityaccess.api.rest.dto.user.CreateUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.user.ChangePasswordCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.user.CreateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.auth.LoginCommand;
import org.seniorcare.identityaccess.application.commands.impl.user.ChangePasswordCommand;
import org.seniorcare.identityaccess.application.commands.impl.user.CreateUserCommand;
import org.seniorcare.identityaccess.application.dto.auth.LoginResult;
import org.seniorcare.identityaccess.application.security.AuthenticatedPrincipal;
import org.seniorcare.identityaccess.application.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Endpoints para Registro e Login")
public class AuthController {

    private final CreateUserCommandHandler createHandler;
    private final AuthenticationService authenticationService;
    private final ChangePasswordCommandHandler changePasswordHandler;

    public AuthController(CreateUserCommandHandler createHandler, AuthenticationService authenticationService,
                          ChangePasswordCommandHandler changePasswordHandler) {
        this.createHandler = createHandler;
        this.authenticationService = authenticationService;
        this.changePasswordHandler = changePasswordHandler;
    }

    @Operation(summary = "Registra um novo usuário")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@Valid @RequestBody CreateUserRequest request) {
        var command = new CreateUserCommand(
                request.name(),
                request.email(),
                request.phone(),
                request.addressId(),
                request.password()
        );

        final UUID newUserId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/users/{id}")
                .buildAndExpand(newUserId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Autentica um usuário e retorna um token JWT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
            @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResult result = authenticationService.login(new LoginCommand(request.email(), request.password()));
        return ResponseEntity.ok(new LoginResponse(
                result.token(),
                result.userId(),
                result.name(),
                result.email(),
                result.role(),
                result.mustChangePassword()
        ));
    }

    @Operation(summary = "Altera a senha do usuário autenticado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "401", description = "Não autenticado")
    })
    @PostMapping("/change-password")
    public ResponseEntity<Void> changePassword(
            @Valid @RequestBody ChangePasswordRequest request,
            @AuthenticationPrincipal AuthenticatedPrincipal principal) {
        changePasswordHandler.handle(new ChangePasswordCommand(principal.getId(), request.newPassword()));
        return ResponseEntity.noContent().build();
    }
}