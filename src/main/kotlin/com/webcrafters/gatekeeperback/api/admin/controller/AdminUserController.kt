package com.webcrafters.gatekeeperback.api.admin.controller

import com.webcrafters.gatekeeperback.api.admin.dto.AppUserResponse
import com.webcrafters.gatekeeperback.api.admin.dto.CreateManagerRequest
import com.webcrafters.gatekeeperback.api.admin.service.AdminUserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/admin/managers")
@Tag(name = "Gerenciamento de Administradores", description = "Endpoints destinados à gestão de usuários com perfil de gerente no sistema de controle de acesso.")
class AdminUserController(
    private val adminUserService: AdminUserService,
) {
    @Operation(
        summary = "Criar novo gerente",
        description = "Cadastra um novo usuário com privilégios de gerente no sistema."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "201", description = "Gerente criado com sucesso"),
        ApiResponse(responseCode = "400", description = "Dados de requisição inválidos"),
        ApiResponse(responseCode = "401", description = "Não autorizado"),
        ApiResponse(responseCode = "403", description = "Acesso proibido")
    ])
    @PostMapping
    fun createManager(@Valid @RequestBody request: CreateManagerRequest): ResponseEntity<AppUserResponse> {
        val createdManager = adminUserService.createManager(request)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdManager)
    }

    @Operation(
        summary = "Listar gerentes",
        description = "Retorna uma lista paginada de todos os gerentes cadastrados."
    )
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "Lista recuperada com sucesso"),
        ApiResponse(responseCode = "401", description = "Não autorizado")
    ])
    @GetMapping
    fun listManagers(pageable: Pageable): ResponseEntity<Page<AppUserResponse>> =
        ResponseEntity.ok(adminUserService.listManagers(pageable))
}
