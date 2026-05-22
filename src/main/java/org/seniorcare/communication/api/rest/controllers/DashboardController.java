package org.seniorcare.communication.api.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.seniorcare.communication.application.queries.dto.DashboardStatsResponse;
import org.seniorcare.communication.application.queries.handlers.GetDashboardStatsQueryHandler;
import org.seniorcare.communication.application.queries.impl.GetDashboardStatsQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@Tag(name = "Dashboard", description = "Endpoints para métricas consolidadas do painel")
public class DashboardController {

    private final GetDashboardStatsQueryHandler statsHandler;

    public DashboardController(GetDashboardStatsQueryHandler statsHandler) {
        this.statsHandler = statsHandler;
    }

    @Operation(summary = "Retorna métricas consolidadas para o painel")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Métricas retornadas com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping("/stats")
    @PreAuthorize("hasAuthority('READ_USER')")
    public ResponseEntity<DashboardStatsResponse> getStats() {
        return ResponseEntity.ok(statsHandler.handle(new GetDashboardStatsQuery()));
    }
}
