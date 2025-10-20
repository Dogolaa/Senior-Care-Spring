package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToDoctorRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToManagerRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToNurseRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.UpdateEmployeeRequest;
import org.seniorcare.identityaccess.application.commands.handlers.employee.*;
import org.seniorcare.identityaccess.application.commands.impl.employee.*;
import org.seniorcare.identityaccess.application.dto.employee.EmployeeDTO;
import org.seniorcare.identityaccess.application.dto.nurse.NurseDTO;
import org.seniorcare.identityaccess.application.queries.handlers.nurse.FindAllNursesQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.nurse.FindNurseByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindAllNursesQuery;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindNurseByIdQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoints para gerenciar funcionários(as)")
public class EmployeeController {

    private final PromoteUserToNurseCommandHandler promoteUserToNurseHandler;
    private final DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler;
    private final PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler;
    private final UpdateEmployeeCommandHandler updateEmployeeHandler;
    private final PromoteUserToManagerCommandHandler promoteUserToManagerHandler;
    private final FindAllNursesQueryHandler findAllNursesHandler;
    private final FindNurseByIdQueryHandler findNurseByIdHandler;
    private final PagedResourcesAssembler<NurseDTO> pagedResourcesAssembler;

    public EmployeeController(
            PromoteUserToNurseCommandHandler promoteUserToNurseHandler,
            DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler,
            PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler,
            UpdateEmployeeCommandHandler updateEmployeeHandler,
            PromoteUserToManagerCommandHandler promoteUserToManagerHandler,
            FindAllNursesQueryHandler findAllNursesHandler,
            FindNurseByIdQueryHandler findNurseByIdHandler,
            PagedResourcesAssembler<NurseDTO> pagedResourcesAssemble
    ) {
        this.promoteUserToNurseHandler = promoteUserToNurseHandler;
        this.demoteEmployeeToUserHandler = demoteEmployeeToUserHandler;
        this.promoteUserToDoctorHandler = promoteUserToDoctorHandler;
        this.updateEmployeeHandler = updateEmployeeHandler;
        this.promoteUserToManagerHandler = promoteUserToManagerHandler;
        this.findAllNursesHandler = findAllNursesHandler;
        this.findNurseByIdHandler = findNurseByIdHandler;
        this.pagedResourcesAssembler = pagedResourcesAssemble;
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

    @Operation(summary = "Promove usuário(a) para gerente")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Usuário promovido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos")})
    @PostMapping("/managers")
    public ResponseEntity<Void> promoteUserToManager(@RequestBody PromoteUserToManagerRequest request) {
        var command = new PromoteUserToManagerCommand(
                request.userId(),
                request.admissionDate(),
                request.department(),
                request.shift()
        );

        promoteUserToManagerHandler.handle(command);

        return ResponseEntity.ok().build();
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
                request.coren(),
                request.department()
        );

        EmployeeDTO updatedEmployee = updateEmployeeHandler.handle(command);

        return ResponseEntity.ok(updatedEmployee);
    }

    @Operation(summary = "Busca um(a) enfermeiro(a) por ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Enfermeiro(a) encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Enfermeiro(a) não encontrado")})
    @GetMapping(value = "/nurses/{id}")
    public ResponseEntity<EntityModel<NurseDTO>> findNurseById(@PathVariable UUID id) {
        var query = new FindNurseByIdQuery(id);

        return findNurseByIdHandler.handle(query)
                .map(this::addLinksToNurse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os enfermeiros(as) com paginação")
    @GetMapping(value = "/nurses")
    public ResponseEntity<PagedModel<EntityModel<NurseDTO>>> findAllNurses(Pageable pageable) {
        var query = new FindAllNursesQuery(pageable);
        Page<NurseDTO> nursesPages = findAllNursesHandler.handle(query);

        PagedModel<EntityModel<NurseDTO>> pagedModel = pagedResourcesAssembler.toModel(nursesPages, this::addLinksToNurse);

        return ResponseEntity.ok(pagedModel);
    }


    private EntityModel<NurseDTO> addLinksToNurse(NurseDTO nurseDto) {
        return EntityModel.of(nurseDto,
                linkTo(methodOn(EmployeeController.class).findNurseById(nurseDto.getId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).demoteEmployeeToUser(nurseDto.getId())).withRel("delete"),
                linkTo(methodOn(EmployeeController.class).updateEmployee(nurseDto.getId(), null)).withRel("update"),
                linkTo(methodOn(EmployeeController.class).findAllNurses(null)).withRel("all-nurses")
        );
    }

}