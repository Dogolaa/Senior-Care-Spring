package org.seniorcare.health.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.health.application.queries.dto.MedicationResponse;
import org.seniorcare.health.application.queries.handlers.SearchMedicationsQueryHandler;
import org.seniorcare.health.application.queries.impl.SearchMedicationsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medications")
@Tag(name = "Medications", description = "Endpoints para consulta do catálogo de medicamentos")
public class MedicationController {

    private final SearchMedicationsQueryHandler searchMedicationsQueryHandler;

    public MedicationController(SearchMedicationsQueryHandler searchMedicationsQueryHandler) {
        this.searchMedicationsQueryHandler = searchMedicationsQueryHandler;
    }

    @Operation(
            summary = "Pesquisa medicamentos no catálogo local",
            description = "Busca medicamentos por nome comercial ou princípio ativo no catálogo interno da plataforma."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de medicamentos encontrados",
                    content = @Content(schema = @Schema(implementation = MedicationResponse.class))
            ),
            @ApiResponse(responseCode = "403", description = "Usuário não autorizado")
    })
    @GetMapping
    public ResponseEntity<List<MedicationResponse>> searchMedications(
            @Parameter(description = "Nome comercial ou princípio ativo para busca", required = true)
            @RequestParam String productName,
            @Parameter(description = "Página (base 1)")
            @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "Quantidade de resultados por página")
            @RequestParam(defaultValue = "10") int count
    ) {
        SearchMedicationsQuery query = new SearchMedicationsQuery(productName, page, count);
        List<MedicationResponse> result = searchMedicationsQueryHandler.handle(query);
        return ResponseEntity.ok(result);
    }
}
