package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.address.CreateAddressRequest;
import org.seniorcare.identityaccess.application.commands.handlers.address.CreateAddressCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.address.CreateAddressCommand;
import org.seniorcare.identityaccess.application.dto.address.AddressDTO;
import org.seniorcare.identityaccess.application.queries.handlers.address.FindAddressByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.address.FindAddressByIdQuery;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Map;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/address")
@Tag(name = "Address", description = "Endpoints para Gerenciamento de Endereços")
public class AddressController {

    private final CreateAddressCommandHandler createHandler;
    private final FindAddressByIdQueryHandler findByIdHandler;

    public AddressController(CreateAddressCommandHandler createHandler, FindAddressByIdQueryHandler findByIdHandler) {
        this.createHandler = createHandler;
        this.findByIdHandler = findByIdHandler;
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


    @Operation(summary = "Busca um endereço por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Endereço  encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")})
    @GetMapping(value = "/{id}")
    public ResponseEntity<EntityModel<AddressDTO>> findAddressById(@PathVariable UUID id) {
        var query = new FindAddressByIdQuery(id);

        return findByIdHandler.handle(query)
                .map(this::addLinksToAddress)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    private EntityModel<AddressDTO> addLinksToAddress(AddressDTO addressDTO) {
        return EntityModel.of(addressDTO,
                linkTo(methodOn(AddressController.class).findAddressById(addressDTO.getId())).withSelfRel()
                // linkTo(methodOn(AddressController.class).deleteUser(addressDTO.getId())).withRel("delete"),
                // linkTo(methodOn(AddressController.class).updateUser(addressDTO.getId(), null)).withRel("update"),
                // linkTo(methodOn(AddressController.class).findAllUsers(null)).withRel("all-addresses")
        );
    }
}