package org.seniorcare.identityaccess.api.rest.controllers;

import org.seniorcare.identityaccess.api.rest.dto.CreateUserRequest;
import org.seniorcare.identityaccess.application.commands.handlers.CreateUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.CreateUserCommand;
import org.seniorcare.identityaccess.application.dto.UserDTO;
import org.seniorcare.identityaccess.application.queries.handlers.FindUserByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.FindUserByIdQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final CreateUserCommandHandler createHandler;
    private final FindUserByIdQueryHandler findHandler;

    public UserController(CreateUserCommandHandler createHandler, FindUserByIdQueryHandler findHandler) {
        this.createHandler = createHandler;
        this.findHandler = findHandler;
    }


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


    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable UUID id) {
        var query = new FindUserByIdQuery(id);

        return findHandler.handle(query)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}