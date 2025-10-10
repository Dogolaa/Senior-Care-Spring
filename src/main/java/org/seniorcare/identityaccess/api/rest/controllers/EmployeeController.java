package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToNurseRequest;
import org.seniorcare.identityaccess.application.commands.handlers.employee.PromoteUserToNurseCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToNurseCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoints para promover usuários(as) para funcionários(as)")
public class EmployeeController {

    private final PromoteUserToNurseCommandHandler promoteUserToNurseHandler;

    public EmployeeController(PromoteUserToNurseCommandHandler promoteUserToNurseHandler) {
        this.promoteUserToNurseHandler = promoteUserToNurseHandler;
    }

    @Operation(summary = "Promove usuário(a) para enfermeiro(a)")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Usuário promovido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping("/nurses")
    public ResponseEntity<Void> promoteUserToNurse(@RequestBody PromoteUserToNurseRequest request) {
        var command = new PromoteUserToNurseCommand(
                request.userId(),
                request.admissionDate(),
                request.coren(),
                request.specialization(),
                request.shift()
        );

        promoteUserToNurseHandler.handle(command);

        return ResponseEntity.ok().build();
    }

}
