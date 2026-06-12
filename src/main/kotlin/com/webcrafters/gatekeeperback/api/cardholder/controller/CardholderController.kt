package com.webcrafters.gatekeeperback.api.cardholder.controller

import com.webcrafters.gatekeeperback.api.cardholder.dto.CardholderAccessLogResponse
import com.webcrafters.gatekeeperback.api.cardholder.service.CardholderService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/cardholder")
@Tag(name = "Portador de Cartão", description = "Endpoints destinados às operações e consultas realizadas pelo próprio portador do cartão de acesso.")
class CardholderController(
    private val cardholderService: CardholderService,
) {

    @Operation(
        summary = "Listar logs de acesso próprios",
        description = "Recupera o histórico paginado de todas as tentativas de acesso (sucesso ou falha) registradas para o usuário autenticado."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista de logs recuperada com sucesso"),
        ApiResponse(responseCode = "401", description = "Usuário não autenticado ou token inválido"),
        ApiResponse(responseCode = "403", description = "Usuário não possui permissão para acessar estes registros")
    ])
    @GetMapping("/access-logs")
    fun listOwnAccessLogs(pageable: Pageable): ResponseEntity<Page<CardholderAccessLogResponse>> =
        ResponseEntity.ok(cardholderService.listOwnAccessLogs(pageable))
}
