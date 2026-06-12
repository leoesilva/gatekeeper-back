package com.webcrafters.gatekeeperback.api.manager.controller

import com.webcrafters.gatekeeperback.api.manager.dto.AccessLogResponse
import com.webcrafters.gatekeeperback.api.manager.dto.AccessPointResponse
import com.webcrafters.gatekeeperback.api.manager.dto.CreateAccessPointRequest
import com.webcrafters.gatekeeperback.api.manager.dto.CreateRfidCredentialRequest
import com.webcrafters.gatekeeperback.api.manager.dto.RfidCredentialResponse
import com.webcrafters.gatekeeperback.api.manager.service.ManagerService
import jakarta.validation.Valid
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@Tag(name = "Gerenciamento", description = "Endpoints destinados à administração do sistema de controle de acesso, incluindo gestão de pontos de acesso, credenciais e auditoria de logs.")
@RequestMapping("/api/manager")
class ManagerController(
    private val managerService: ManagerService,
) {
    @Operation(summary = "Criar ponto de acesso", description = "Cadastra um novo dispositivo ou local de controle de acesso (ex: Porta Principal, Catraca).")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Ponto de acesso criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
        ApiResponse(responseCode = "401", description = "Não autorizado")
    ])
    @PostMapping("/access-points")
    fun createAccessPoint(
        @Valid @RequestBody request: CreateAccessPointRequest,
    ): ResponseEntity<AccessPointResponse> {
        val createdAccessPoint = managerService.createAccessPoint(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAccessPoint)
    }

    @Operation(summary = "Listar pontos de acesso", description = "Retorna uma lista paginada de todos os pontos de acesso configurados no sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
        ApiResponse(responseCode = "401", description = "Não autorizado")
    ])
    @GetMapping("/access-points")
    fun listAccessPoints(pageable: Pageable): ResponseEntity<Page<AccessPointResponse>> {
        val response = managerService.listAccessPoints(pageable)
        return ResponseEntity.ok(response)
    }

    @Operation(summary = "Criar credencial RFID", description = "Vincula uma nova tag ou cartão RFID a um usuário no sistema.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Credencial criada com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados da requisição inválidos"),
        ApiResponse(responseCode = "401", description = "Não autorizado"),
        ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    ])
    @PostMapping("/rfid-credentials")
    fun createRfidCredential(
        @Valid @RequestBody request: CreateRfidCredentialRequest,
    ): ResponseEntity<RfidCredentialResponse> {
        val createdCredential = managerService.createRfidCredential(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCredential)
    }

    @Operation(summary = "Listar logs de acesso", description = "Recupera o histórico paginado de entradas e saídas registradas pelos pontos de acesso.")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Logs recuperados com sucesso"),
        ApiResponse(responseCode = "401", description = "Não autorizado")
    ])
    @GetMapping("/access-logs")
    fun listAccessLogs(pageable: Pageable): ResponseEntity<Page<AccessLogResponse>> {
        val response = managerService.listAccessLogs(pageable)
        return ResponseEntity.ok(response)
    }
}
