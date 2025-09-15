package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.CreateUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.CreateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.DeleteUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.CreateUserCommand;
import org.seniorcare.identityaccess.application.commands.impl.DeleteUserCommand;
import org.seniorcare.identityaccess.application.dto.UserDTO;
import org.seniorcare.identityaccess.application.queries.handlers.FindUserByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.FindUserByIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints para Gerenciamento de Usuários")
public class UserController {

    private final CreateUserCommandHandler createHandler;
    private final FindUserByIdQueryHandler findHandler;
    private final DeleteUserCommandHandler deleteHandler;

    public UserController(CreateUserCommandHandler createHandler, FindUserByIdQueryHandler findHandler, DeleteUserCommandHandler deleteHandler) {
        this.createHandler = createHandler;
        this.findHandler = findHandler;
        this.deleteHandler = deleteHandler;
    }

    @Operation(summary = "Cria um novo usuário", description = "Endpoint para registrar um novo usuário no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")
    })
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        var command = new CreateUserCommand(
                request.name(),
                request.email(),
                request.phone(),
                request.addressId(),
                request.password(),
                request.roleId()
        );
        createHandler.handle(command);
        return ResponseEntity.created(URI.create("")).build();
    }

    @Operation(summary = "Busca um usuário por ID", description = "Retorna os dados públicos de um usuário específico com base no seu UUID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @GetMapping(value = "/{id}", produces = {"application/hal+json"})
    public ResponseEntity<UserDTO> findUserById(@PathVariable UUID id) {
        var query = new FindUserByIdQuery(id);
        return findHandler.handle(query)
                .map(userDTO -> {

                    userDTO.add(linkTo(methodOn(UserController.class).findUserById(id)).withSelfRel());

                    // userDTO.add(linkTo(methodOn(UserController.class).findAllUsers()).withRel("all-users"));

                    return ResponseEntity.ok(userDTO);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os usuários", description = "Retorna uma lista de todos os usuários cadastrados.")
    @GetMapping(produces = {"application/hal+json"})
    public ResponseEntity<List<UserDTO>> findAllUsers() {
        // TODO: Implementar a lógica de busca de todos os usuários (Query + Handler)
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Deleta um usuário (Soft Delete)", description = "Realiza a exclusão lógica de um usuário pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        var command = new DeleteUserCommand(id);

        deleteHandler.handle(command);

        return ResponseEntity.noContent().build();

    }
}