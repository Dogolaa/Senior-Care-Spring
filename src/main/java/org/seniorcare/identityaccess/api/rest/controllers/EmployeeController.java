package org.seniorcare.identityaccess.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToDoctorRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToManagerRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.PromoteUserToNurseRequest;
import org.seniorcare.identityaccess.api.rest.dto.employee.UpdateEmployeeRequest;
import org.seniorcare.identityaccess.application.commands.handlers.employee.*;
import org.seniorcare.identityaccess.application.commands.impl.employee.*;
import org.seniorcare.identityaccess.application.dto.doctor.DoctorDTO;
import org.seniorcare.identityaccess.application.dto.employee.EmployeeDTO;
import org.seniorcare.identityaccess.application.dto.employee.EmployeeDetailsDTO;
import org.seniorcare.identityaccess.application.dto.manager.ManagerDTO;
import org.seniorcare.identityaccess.application.dto.nurse.NurseDTO;
import org.seniorcare.identityaccess.application.queries.handlers.doctor.FindAllDoctorsQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.doctor.FindDoctorByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.employee.FindAllEmployeesQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.employee.FindEmployeeByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.manager.FindAllManagersQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.manager.FindManagerByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.nurse.FindAllNursesQueryHandler;
import org.seniorcare.identityaccess.application.queries.handlers.nurse.FindNurseByIdQueryHandler;
import org.seniorcare.identityaccess.application.queries.impl.doctor.FindAllDoctorsQuery;
import org.seniorcare.identityaccess.application.queries.impl.doctor.FindDoctorByIdQuery;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindAllEmployeesQuery;
import org.seniorcare.identityaccess.application.queries.impl.employee.FindEmployeeByIdQuery;
import org.seniorcare.identityaccess.application.queries.impl.manager.FindAllManagersQuery;
import org.seniorcare.identityaccess.application.queries.impl.manager.FindManagerByIdQuery;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindAllNursesQuery;
import org.seniorcare.identityaccess.application.queries.impl.nurse.FindNurseByIdQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Endpoints para gerenciar funcionários(as)")
public class EmployeeController {

    // Command Handlers
    private final PromoteUserToNurseCommandHandler promoteUserToNurseHandler;
    private final DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler;
    private final PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler;
    private final UpdateEmployeeCommandHandler updateEmployeeHandler;
    private final PromoteUserToManagerCommandHandler promoteUserToManagerHandler;

    // Query Handlers
    private final FindAllNursesQueryHandler findAllNursesHandler;
    private final FindNurseByIdQueryHandler findNurseByIdHandler;
    private final FindAllDoctorsQueryHandler findAllDoctorsHandler;
    private final FindDoctorByIdQueryHandler findDoctorByIdHandler;
    private final FindAllManagersQueryHandler findAllManagersHandler;
    private final FindManagerByIdQueryHandler findManagerByIdHandler;
    private final FindAllEmployeesQueryHandler findAllEmployeesHandler;
    private final FindEmployeeByIdQueryHandler findEmployeeByIdHandler;


    // HATEOAS Assemblers
    private final PagedResourcesAssembler<NurseDTO> nursePagedResourcesAssembler;
    private final PagedResourcesAssembler<DoctorDTO> doctorPagedResourcesAssembler;
    private final PagedResourcesAssembler<ManagerDTO> managerPagedResourcesAssembler;
    private final PagedResourcesAssembler<EmployeeDetailsDTO> employeePagedResourcesAssembler;


    public EmployeeController(
            PromoteUserToNurseCommandHandler promoteUserToNurseHandler,
            DemoteEmployeeToUserCommandHandler demoteEmployeeToUserHandler,
            PromoteUserToDoctorCommandHandler promoteUserToDoctorHandler,
            UpdateEmployeeCommandHandler updateEmployeeHandler,
            PromoteUserToManagerCommandHandler promoteUserToManagerHandler,
            FindAllNursesQueryHandler findAllNursesHandler,
            FindNurseByIdQueryHandler findNurseByIdHandler,
            FindAllDoctorsQueryHandler findAllDoctorsHandler,
            FindDoctorByIdQueryHandler findDoctorByIdHandler,
            FindAllManagersQueryHandler findAllManagersHandler,
            FindManagerByIdQueryHandler findManagerByIdHandler,
            FindAllEmployeesQueryHandler findAllEmployeesHandler,
            FindEmployeeByIdQueryHandler findEmployeeByIdHandler,
            PagedResourcesAssembler<NurseDTO> nursePagedResourcesAssembler,
            PagedResourcesAssembler<DoctorDTO> doctorPagedResourcesAssembler,
            PagedResourcesAssembler<ManagerDTO> managerPagedResourcesAssembler,
            PagedResourcesAssembler<EmployeeDetailsDTO> employeePagedResourcesAssembler
    ) {
        this.promoteUserToNurseHandler = promoteUserToNurseHandler;
        this.demoteEmployeeToUserHandler = demoteEmployeeToUserHandler;
        this.promoteUserToDoctorHandler = promoteUserToDoctorHandler;
        this.updateEmployeeHandler = updateEmployeeHandler;
        this.promoteUserToManagerHandler = promoteUserToManagerHandler;
        this.findAllNursesHandler = findAllNursesHandler;
        this.findNurseByIdHandler = findNurseByIdHandler;
        this.findAllDoctorsHandler = findAllDoctorsHandler;
        this.findDoctorByIdHandler = findDoctorByIdHandler;
        this.findAllManagersHandler = findAllManagersHandler;
        this.findManagerByIdHandler = findManagerByIdHandler;
        this.findAllEmployeesHandler = findAllEmployeesHandler;
        this.findEmployeeByIdHandler = findEmployeeByIdHandler;
        this.nursePagedResourcesAssembler = nursePagedResourcesAssembler;
        this.doctorPagedResourcesAssembler = doctorPagedResourcesAssembler;
        this.managerPagedResourcesAssembler = managerPagedResourcesAssembler;
        this.employeePagedResourcesAssembler = employeePagedResourcesAssembler;
    }

    @Operation(summary = "Promove usuário(a) para enfermeiro(a)")
    @PostMapping("/nurses")
    @PreAuthorize("hasAuthority('MANAGE_EMPLOYEES')")
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
    @PostMapping("/managers")
    @PreAuthorize("hasAuthority('MANAGE_EMPLOYEES')")
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
    @PostMapping("/doctors")
    @PreAuthorize("hasAuthority('MANAGE_EMPLOYEES')")
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
    @DeleteMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('MANAGE_EMPLOYEES')")
    public ResponseEntity<Void> demoteEmployeeToUser(@PathVariable UUID employeeId) {
        var command = new DemoteEmployeeToUserCommand(employeeId);
        demoteEmployeeToUserHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Atualiza dados de um(a) funcionário(a)")
    @PutMapping("/{employeeId}")
    @PreAuthorize("hasAuthority('MANAGE_EMPLOYEES')")
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

    @Operation(summary = "Busca um(a) funcionário(a) genérico por ID")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAnyAuthority('READ_EMPLOYEE_DETAILS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<EntityModel<EmployeeDetailsDTO>> findEmployeeById(@PathVariable UUID id) {
        var query = new FindEmployeeByIdQuery(id);
        return findEmployeeByIdHandler.handle(query)
                .map(this::addLinksToEmployee)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os funcionários(as) com paginação")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('READ_ALL_EMPLOYEES', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<PagedModel<EntityModel<EmployeeDetailsDTO>>> findAllEmployees(Pageable pageable) {
        var query = new FindAllEmployeesQuery(pageable);
        Page<EmployeeDetailsDTO> employeePages = findAllEmployeesHandler.handle(query);
        PagedModel<EntityModel<EmployeeDetailsDTO>> pagedModel = employeePagedResourcesAssembler.toModel(employeePages, this::addLinksToEmployee);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Busca um(a) enfermeiro(a) por ID")
    @GetMapping(value = "/nurses/{id}")
    @PreAuthorize("hasAnyAuthority('READ_NURSE_DETAILS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<EntityModel<NurseDTO>> findNurseById(@PathVariable UUID id) {
        var query = new FindNurseByIdQuery(id);
        return findNurseByIdHandler.handle(query)
                .map(this::addLinksToNurse)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os enfermeiros(as) com paginação")
    @GetMapping(value = "/nurses")
    @PreAuthorize("hasAnyAuthority('READ_ALL_NURSES', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<PagedModel<EntityModel<NurseDTO>>> findAllNurses(Pageable pageable) {
        var query = new FindAllNursesQuery(pageable);
        Page<NurseDTO> nursesPages = findAllNursesHandler.handle(query);
        PagedModel<EntityModel<NurseDTO>> pagedModel = nursePagedResourcesAssembler.toModel(nursesPages, this::addLinksToNurse);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Busca um(a) gerente por ID")
    @GetMapping(value = "/managers/{id}")
    @PreAuthorize("hasAnyAuthority('READ_MANAGER_DETAILS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<EntityModel<ManagerDTO>> findManagerById(@PathVariable UUID id) {
        var query = new FindManagerByIdQuery(id);
        return findManagerByIdHandler.handle(query)
                .map(this::addLinksToManager)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os gerentes com paginação")
    @GetMapping(value = "/managers")
    @PreAuthorize("hasAnyAuthority('READ_ALL_MANAGERS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<PagedModel<EntityModel<ManagerDTO>>> findAllManagers(Pageable pageable) {
        var query = new FindAllManagersQuery(pageable);
        Page<ManagerDTO> managersPage = findAllManagersHandler.handle(query);
        PagedModel<EntityModel<ManagerDTO>> pagedModel = managerPagedResourcesAssembler.toModel(managersPage, this::addLinksToManager);
        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Busca um(a) médico(a) por ID")
    @GetMapping(value = "/doctors/{id}")
    @PreAuthorize("hasAnyAuthority('READ_DOCTOR_DETAILS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<EntityModel<DoctorDTO>> findDoctorById(@PathVariable UUID id) {
        var query = new FindDoctorByIdQuery(id);
        return findDoctorByIdHandler.handle(query)
                .map(this::addLinksToDoctor)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Lista todos os médicos(as) com paginação")
    @GetMapping(value = "/doctors")
    @PreAuthorize("hasAnyAuthority('READ_ALL_DOCTORS', 'MANAGE_EMPLOYEES')")
    public ResponseEntity<PagedModel<EntityModel<DoctorDTO>>> findAllDoctors(Pageable pageable) {
        var query = new FindAllDoctorsQuery(pageable);
        Page<DoctorDTO> doctorPages = findAllDoctorsHandler.handle(query);
        PagedModel<EntityModel<DoctorDTO>> pagedModel = doctorPagedResourcesAssembler.toModel(doctorPages, this::addLinksToDoctor);
        return ResponseEntity.ok(pagedModel);
    }


    private EntityModel<EmployeeDetailsDTO> addLinksToEmployee(EmployeeDetailsDTO employeeDto) {
        return EntityModel.of(employeeDto,
                linkTo(methodOn(EmployeeController.class).findEmployeeById(employeeDto.employeeId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).demoteEmployeeToUser(employeeDto.employeeId())).withRel("demote"),
                linkTo(methodOn(EmployeeController.class).updateEmployee(employeeDto.employeeId(), null)).withRel("update"),
                linkTo(methodOn(EmployeeController.class).findAllEmployees(null)).withRel("all-employees")
        );
    }

    private EntityModel<NurseDTO> addLinksToNurse(NurseDTO nurseDto) {
        return EntityModel.of(nurseDto,
                linkTo(methodOn(EmployeeController.class).findNurseById(nurseDto.getEmployeeId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).demoteEmployeeToUser(nurseDto.getEmployeeId())).withRel("demote"),
                linkTo(methodOn(EmployeeController.class).updateEmployee(nurseDto.getEmployeeId(), null)).withRel("update"),
                linkTo(methodOn(EmployeeController.class).findAllNurses(null)).withRel("all-nurses")
        );
    }

    private EntityModel<DoctorDTO> addLinksToDoctor(DoctorDTO doctorDto) {
        return EntityModel.of(doctorDto,
                linkTo(methodOn(EmployeeController.class).findDoctorById(doctorDto.getEmployeeId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).demoteEmployeeToUser(doctorDto.getEmployeeId())).withRel("demote"),
                linkTo(methodOn(EmployeeController.class).updateEmployee(doctorDto.getEmployeeId(), null)).withRel("update"),
                linkTo(methodOn(EmployeeController.class).findAllDoctors(null)).withRel("all-doctors")
        );
    }

    private EntityModel<ManagerDTO> addLinksToManager(ManagerDTO managerDto) {
        return EntityModel.of(managerDto,
                linkTo(methodOn(EmployeeController.class).findManagerById(managerDto.getEmployeeId())).withSelfRel(),
                linkTo(methodOn(EmployeeController.class).demoteEmployeeToUser(managerDto.getEmployeeId())).withRel("demote"),
                linkTo(methodOn(EmployeeController.class).updateEmployee(managerDto.getEmployeeId(), null)).withRel("update"),
                linkTo(methodOn(EmployeeController.class).findAllManagers(null)).withRel("all-managers")
        );
    }
}