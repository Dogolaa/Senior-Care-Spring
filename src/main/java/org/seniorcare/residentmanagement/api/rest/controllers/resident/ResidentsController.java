package org.seniorcare.residentmanagement.api.rest.controllers.resident;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.seniorcare.residentmanagement.api.rest.dto.AddAllergyRequest;
import org.seniorcare.residentmanagement.api.rest.dto.AddFamilyLinkRequest;
import org.seniorcare.residentmanagement.api.rest.dto.AdmitResidentRequest;
import org.seniorcare.residentmanagement.api.rest.dto.UpdateResidentRequest;
import org.seniorcare.residentmanagement.application.commands.handlers.familyLink.AddFamilyLinkCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.familyLink.RemoveFamilyLinkCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.familyLink.SetPrimaryContactCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.AddAllergyCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.AdmitResidentCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.DischargeResidentCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.RemoveAllergyCommandHandler;
import org.seniorcare.residentmanagement.application.commands.handlers.resident.UpdateResidentCommandHandler;
import org.seniorcare.residentmanagement.application.commands.impl.familyLink.AddFamilyLinkCommand;
import org.seniorcare.residentmanagement.application.commands.impl.familyLink.RemoveFamilyLinkCommand;
import org.seniorcare.residentmanagement.application.commands.impl.familyLink.SetPrimaryContactCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.AddAllergyCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.AdmitResidentCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.DischargeResidentCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.RemoveAllergyCommand;
import org.seniorcare.residentmanagement.application.commands.impl.resident.UpdateResidentCommand;
import org.seniorcare.residentmanagement.application.dto.resident.ResidentDTO;
import org.seniorcare.residentmanagement.application.queries.handlers.resident.FindAllResidentsQueryHandler;
import org.seniorcare.residentmanagement.application.queries.handlers.resident.FindResidentByIdQueryHandler;
import org.seniorcare.residentmanagement.application.queries.impl.resident.FindAllResidentsQuery;
import org.seniorcare.residentmanagement.application.queries.impl.resident.FindResidentByIdQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/residents")
@Tag(name = "Resident Management", description = "Endpoints para Gerenciamento de Residentes")
public class ResidentsController {

    private final AdmitResidentCommandHandler admitResidentHandler;
    private final UpdateResidentCommandHandler updateResidentHandler;
    private final DischargeResidentCommandHandler dischargeResidentHandler;
    private final AddFamilyLinkCommandHandler addFamilyLinkHandler;
    private final RemoveFamilyLinkCommandHandler removeFamilyLinkHandler;
    private final SetPrimaryContactCommandHandler setPrimaryContactHandler;
    private final AddAllergyCommandHandler addAllergyHandler;
    private final RemoveAllergyCommandHandler removeAllergyHandler;
    private final FindAllResidentsQueryHandler findAllResidentsHandler;
    private final FindResidentByIdQueryHandler findResidentByIdHandler;
    private final PagedResourcesAssembler<ResidentDTO> pagedResourcesAssembler;

    public ResidentsController(
            AdmitResidentCommandHandler admitResidentHandler,
            UpdateResidentCommandHandler updateResidentHandler,
            DischargeResidentCommandHandler dischargeResidentHandler,
            AddFamilyLinkCommandHandler addFamilyLinkHandler,
            RemoveFamilyLinkCommandHandler removeFamilyLinkHandler,
            SetPrimaryContactCommandHandler setPrimaryContactHandler,
            AddAllergyCommandHandler addAllergyHandler,
            RemoveAllergyCommandHandler removeAllergyHandler,
            FindAllResidentsQueryHandler findAllResidentsHandler,
            FindResidentByIdQueryHandler findResidentByIdHandler,
            PagedResourcesAssembler<ResidentDTO> pagedResourcesAssembler
    ) {
        this.admitResidentHandler = admitResidentHandler;
        this.updateResidentHandler = updateResidentHandler;
        this.dischargeResidentHandler = dischargeResidentHandler;
        this.addFamilyLinkHandler = addFamilyLinkHandler;
        this.removeFamilyLinkHandler = removeFamilyLinkHandler;
        this.setPrimaryContactHandler = setPrimaryContactHandler;
        this.addAllergyHandler = addAllergyHandler;
        this.removeAllergyHandler = removeAllergyHandler;
        this.findAllResidentsHandler = findAllResidentsHandler;
        this.findResidentByIdHandler = findResidentByIdHandler;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    @Operation(summary = "Lista todos os residentes com paginação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de residentes retornada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<PagedModel<EntityModel<ResidentDTO>>> findAllResidents(Pageable pageable) {
        var query = new FindAllResidentsQuery(pageable);
        Page<ResidentDTO> page = findAllResidentsHandler.handle(query);

        PagedModel<EntityModel<ResidentDTO>> pagedModel = pagedResourcesAssembler.toModel(page, this::addLinksToResident);

        return ResponseEntity.ok(pagedModel);
    }

    @Operation(summary = "Busca um residente pelo ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Residente encontrado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<EntityModel<ResidentDTO>> findResidentById(@PathVariable UUID id) {
        var query = new FindResidentByIdQuery(id);

        return findResidentByIdHandler.handle(query)
                .map(this::addLinksToResident)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Admite um novo residente no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Residente admitido com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos ou CPF duplicado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/admit")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> admitResident(@Valid @RequestBody AdmitResidentRequest request) {
        var command = new AdmitResidentCommand(
                request.name(),
                request.cpf(),
                request.rg(),
                request.dateOfBirth(),
                request.gender(),
                request.bloodType(),
                request.initialAllergies(),
                request.room()
        );

        final UUID newResidentId = admitResidentHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/v1/residents/{id}")
                .buildAndExpand(newResidentId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualiza os dados de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Residente atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<EntityModel<ResidentDTO>> updateResident(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateResidentRequest request) {

        var command = new UpdateResidentCommand(
                id,
                request.name(),
                request.gender(),
                request.bloodType(),
                request.room()
        );

        ResidentDTO updatedResident = updateResidentHandler.handle(command);

        return ResponseEntity.ok(addLinksToResident(updatedResident));
    }

    @Operation(summary = "Realiza a alta (soft delete) de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alta realizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> dischargeResident(@PathVariable UUID id) {
        var command = new DischargeResidentCommand(id);
        dischargeResidentHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona um novo vínculo familiar a um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Vínculo familiar criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados de entrada inválidos"),
            @ApiResponse(responseCode = "404", description = "Residente ou usuário familiar não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/{residentId}/family-links")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> addFamilyLink(
            @PathVariable UUID residentId,
            @Valid @RequestBody AddFamilyLinkRequest request) {

        var command = new AddFamilyLinkCommand(
                residentId,
                request.familyMemberId(),
                request.relationship(),
                request.isPrimaryContact()
        );

        final UUID newFamilyLinkId = addFamilyLinkHandler.handle(command);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(newFamilyLinkId).toUri();

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Remove um vínculo familiar de um residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Vínculo familiar removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Regra de negócio violada (ex: último contato primário)"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{residentId}/family-links/{familyLinkId}")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> removeFamilyLink(
            @PathVariable UUID residentId,
            @PathVariable UUID familyLinkId) {

        var command = new RemoveFamilyLinkCommand(residentId, familyLinkId);
        removeFamilyLinkHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Define um familiar como contato primário do residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Contato primário definido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "400", description = "Vínculo familiar não encontrado para este residente"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PatchMapping("/{residentId}/family-links/{familyLinkId}/primary-contact")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> setPrimaryContact(
            @PathVariable UUID residentId,
            @PathVariable UUID familyLinkId) {

        var command = new SetPrimaryContactCommand(residentId, familyLinkId);
        setPrimaryContactHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Adiciona uma alergia ao prontuário do residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia adicionada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @PostMapping("/{residentId}/allergies")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> addAllergy(
            @PathVariable UUID residentId,
            @Valid @RequestBody AddAllergyRequest request) {

        var command = new AddAllergyCommand(residentId, request.allergyDescription());
        addAllergyHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Remove uma alergia do prontuário do residente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Alergia removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Residente não encontrado"),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @DeleteMapping("/{residentId}/allergies/{allergyDescription}")
    @PreAuthorize("hasAuthority('MANAGE_RESIDENTS')")
    public ResponseEntity<Void> removeAllergy(
            @PathVariable UUID residentId,
            @PathVariable String allergyDescription) {

        var command = new RemoveAllergyCommand(residentId, allergyDescription);
        removeAllergyHandler.handle(command);
        return ResponseEntity.noContent().build();
    }

    private EntityModel<ResidentDTO> addLinksToResident(ResidentDTO dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ResidentsController.class).findResidentById(dto.getId())).withSelfRel(),
                linkTo(methodOn(ResidentsController.class).updateResident(dto.getId(), null)).withRel("update"),
                linkTo(methodOn(ResidentsController.class).dischargeResident(dto.getId())).withRel("discharge"),
                linkTo(methodOn(ResidentsController.class).findAllResidents(null)).withRel("all-residents")
        );
    }
}
