package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.address.CreateAddressRequest;
import org.seniorcare.identityaccess.application.commands.handlers.address.CreateAddressCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.address.CreateAddressCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
@Tag(name = "Address", description = "Endpoints para Gerenciamento de Endereços")
public class AddressController {

    private final CreateAddressCommandHandler createHandler;

    public AddressController(CreateAddressCommandHandler createHandler) {
        this.createHandler = createHandler;
    }

    @Operation(summary = "Cria um novo endereço e retorna seu ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Endereço criado com sucesso"), @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping

    public ResponseEntity<Map<String, UUID>> createAddress(@RequestBody CreateAddressRequest request) {
        var command = new CreateAddressCommand(request.cep(), request.country(), request.state(), request.city(), request.district()
                , request.street(), request.number(), request.complement());

        final UUID newAddressId = createHandler.handle(command);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(newAddressId).toUri();

        Map<String, UUID> responseBody = Map.of("id", newAddressId);

        return ResponseEntity.created(location).body(responseBody);
    }
}