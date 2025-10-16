package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToDoctorRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToNurseRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.UpdateEmployeeRequest;
import org.seniorcare.identityaccess.application.commands.handlers.employee.DemoteEmployeeToUserCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.employee.PromoteUserToDoctorCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.employee.PromoteUserToNurseCommandHandler;
import org.seniorcare.identityaccess.application.commands.handlers.employee.UpdateEmployeeCommandHandler;
import org.seniorcare.identityaccess.application.commands.impl.employee.DemoteEmployeeToUserCommand;
import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToDoctorCommand;
import org.seniorcare.identityaccess.application.commands.impl.employee.PromoteUserToNurseCommand;
import org.seniorcare.identityaccess.application.commands.impl.employee.UpdateEmployeeCommand;
import org.seniorcare.identityaccess.application.dto.employee.EmployeeDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoints para gerenciar funcionários(as)")
public class EmployeeController {

    private final PromoteUserToNurseCommandHandler promoteUserToNurseHandler;
    private final DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler;
    private final PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler;
    private final UpdateEmployeeCommandHandler updateEmployeeHandler;

    public EmployeeController(
            PromoteUserToNurseCommandHandler promoteUserToNurseHandler,
            DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler,
            PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler,
            UpdateEmployeeCommandHandler updateEmployeeHandler
    ) {
        this.promoteUserToNurseHandler = promoteUserToNurseHandler;
        this.demoteEmployeeToUserHandler = demoteEmployeeToUserHandler;
        this.promoteUserToDoctorHandler = promoteUserToDoctorHandler;
        this.updateEmployeeHandler = updateEmployeeHandler;
    }

    @Operation(summary = "Atualiza dados de um(a) funcionário(a)")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Funcionário(a) atualizado(a) com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Funcionário(a) não encontrado(a)")
    })
    @PutMapping("/{employeeId}")
    public ResponseEntity<EmployeeDTO> updateEmployee(
            @PathVariable UUID employeeId,
            @Valid @RequestBody UpdateEmployeeRequest request) {

        var command = new UpdateEmployeeCommand(
                employeeId,
                request.admissionDate(),
                request.specialization(),
                request.shift(),
                request.crm(),
                request.coren()
        );

        EmployeeDTO updatedEmployee = updateEmployeeHandler.handle(command);

        return ResponseEntity.ok(updatedEmployee);
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

    @Operation(summary = "Rebaixa funcionário(a) para usuário(a) padrão")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Funcionário(a) rebaixado(a) com sucesso"),
            @ApiResponse(responseCode = "404", description = "Funcionário(a) não encontrado(a)")
    })
    @DeleteMapping("/{employeeId}")
    public ResponseEntity<Void> demoteEmployeeToUser(@PathVariable UUID employeeId) {
        var command = new DemoteEmployeeToUserCommand(employeeId);
        demoteEmployeeToUserHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Promove usuário(a) para médico(a)")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Usuário promovido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping("/doctors")
    public ResponseEntity<Void> promoteUserToDoctor(@RequestBody PromoteUserToDoctorRequest request) {
        var command = new PromoteUserToDoctorCommand(
                request.userId(),
                request.admissionDate(),
                request.crm(),
                request.specialization(),
                request.shift()
        );

        promoteUserToDoctorHandler.handle(command);

        return ResponseEntity.ok().build();
    }
}