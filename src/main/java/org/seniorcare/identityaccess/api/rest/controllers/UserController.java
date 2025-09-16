package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.user.CreateUserRequest;
import org.seniorcare.identityaccess.api.rest.dto.user.UpdateUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.user.CreateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.user.DeleteUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.user.UpdateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.user.CreateUserCommand;
import org.seniorcare.identityaccess.application.commands.impl.user.DeleteUserCommand;
import org.seniorcare.identityaccess.application.commands.impl.user.UpdateUserCommand;
import org.seniorcare.identityaccess.application.dto.user.UserDTO;
import org.seniorcare.identityaccess.application.queries.handlers.user.FindAllUsersQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.user.FindUserByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.user.FindAllUsersQuery;
import org.seniorcare.identityaccess.application.queries.impl.user.FindUserByIdQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users", description = "Endpoints para Gerenciamento de Usuários")
public class UserController {

    private final CreateUserCommandHandler createHandler;
    private final UpdateUserCommandHandler updateHandler;
    private final DeleteUserCommandHandler deleteHandler;
    private final FindUserByIdQueryHandler findByIdHandler;
    private final FindAllUsersQueryHandler findAllHandler;
    private final PagedResourcesAssembler<UserDTO> pagedResourcesAssembler;


    public UserController(CreateUserCommandHandler createHandler, UpdateUserCommandHandler updateHandler,
                          DeleteUserCommandHandler deleteHandler, FindUserByIdQueryHandler findByIdHandler,
                          FindAllUsersQueryHandler findAllHandler, PagedResourcesAssembler<UserDTO> pagedResourcesAssembler) {
        this.createHandler = createHandler;
        this.updateHandler = updateHandler;
        this.deleteHandler = deleteHandler;
        this.findByIdHandler = findByIdHandler;
        this.findAllHandler = findAllHandler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Operation(summary = "Cria um novo usuário")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody CreateUserRequest request) {
        var command = new CreateUserCommand(request.name(), request.email(), request.phone(), request.addressId(), request.password(), request.roleId());
        final UUID newUserId = createHandler.handle(command);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newUserId).toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Busca um usuário por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuário encontrado com sucesso"), @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<UserDTO>> findUserById(@PathVariable UUID id) {
        var query = new FindUserByIdQuery(id);

        return findByIdHandler.handle(query)
                .map(this::addLinksToUser)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os usuários com paginação")
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<UserDTO>>> findAllUsers(Pageable pageable) {
        var query = new FindAllUsersQuery(pageable);
        Page<UserDTO> usersPage = findAllHandler.handle(query);

        PagedModel<EntityModel<UserDTO>> pagedModel = pagedResourcesAssembler.toModel(usersPage, this::addLinksToUser);

        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Atualiza um usuário")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"), @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<UserDTO>> updateUser(@PathVariable UUID id, @RequestBody UpdateUserRequest request) {
        var command = new UpdateUserCommand(id, request.name(), request.email(), request.phone(), request.isActive(), request.addressId(), request.roleId());
        UserDTO updatedUserDTO = updateHandler.handle(command);

        EntityModel<UserDTO> entityModel = addLinksToUser(updatedUserDTO);

        return ResponseEntity.ok(entityModel);
    }

    @Operation(summary = "Deleta um usuário (Soft Delete)")
    @ApiResponses(value = {@ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"), @ApiResponse(responseCode = "404", description = "Usuário não encontrado")})
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") UUID id) {
        var command = new DeleteUserCommand(id);
        deleteHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<UserDTO> addLinksToUser(UserDTO userDTO) {
        return EntityModel.of(userDTO,
                linkTo(methodOn(UserController.class).findUserById(userDTO.getId())).withSelfRel(),
                linkTo(methodOn(UserController.class).deleteUser(userDTO.getId())).withRel("delete"),
                linkTo(methodOn(UserController.class).updateUser(userDTO.getId(), null)).withRel("update"),
                linkTo(methodOn(UserController.class).findAllUsers(null)).withRel("all-users")
        );
    }
}