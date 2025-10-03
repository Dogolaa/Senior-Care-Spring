package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.application.dto.role.RoleDTO;
import org.seniorcare.identityaccess.application.queries.handlers.role.FindAllRolesQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.role.FindAllRolesQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Endpoints para Consulta de Papéis")
public class RoleController {

    private final FindAllRolesQueryHandler findAllRolesHandler;

    public RoleController(FindAllRolesQueryHandler findAllRolesHandler) {
        this.findAllRolesHandler = findAllRolesHandler;
    }

    @Operation(summary = "Lista todos os papéis disponíveis no sistema")
    @GetMapping
    public ResponseEntity<List<RoleDTO>> findAllRoles() {
        var query = new FindAllRolesQuery();
        List<RoleDTO> roles = findAllRolesHandler.handle(query);
        return ResponseEntity.ok(roles);
    }
}