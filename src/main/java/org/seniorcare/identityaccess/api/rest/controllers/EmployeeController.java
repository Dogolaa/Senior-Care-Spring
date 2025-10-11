package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToNurseRequest;
import org.seniorcare.identityaccess.application.commands.handlers.employee.DemoteNurseToUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.employee.PromoteUserToNurseCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.employee.DemoteNurseToUserCommand;
import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToNurseCommand;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoints para promover usuários(as) para funcionários(as)")
public class EmployeeController {

    private final PromoteUserToNurseCommandHandler promoteUserToNurseHandler;
    private final DemoteNurseToUserCommandHandler demoteNurseToUserHandler;

    public EmployeeController(
            PromoteUserToNurseCommandHandler promoteUserToNurseHandler,
            DemoteNurseToUserCommandHandler demoteNurseToUserHandler
    ) {
        this.promoteUserToNurseHandler = promoteUserToNurseHandler;
        this.demoteNurseToUserHandler = demoteNurseToUserHandler;
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

    @Operation(summary = "Rebaixa enfermeiro(a) para usuário(a) padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Enfermeiro(a) rebaixado(a) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário(a) não encontrado(a)")
    })
    @DeleteMapping("/nurses/{employeeId}")
    public ResponseEntity<Void> demoteNurseToUser(@PathVariable UUID employeeId) {

        var command = new DemoteNurseToUserCommand(employeeId);

        demoteNurseToUserHandler.handle(command);

        return ResponseEntity.noContent().build();
    }

}
