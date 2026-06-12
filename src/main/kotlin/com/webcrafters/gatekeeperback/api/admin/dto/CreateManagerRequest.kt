package com.webcrafters.gatekeeperback.api.admin.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Objeto de requisição para criação de um novo administrador/gestor no sistema.")
data class CreateManagerRequest(
    @field:NotBlank(message = "O nome completo é obrigatório.")
    @field:Schema(description = "Nome completo do gestor.", example = "Ricardo Oliveira")
    val fullName: String,

    @field:NotBlank(message = "O e-mail é obrigatório.")
    @field:Email(message = "O e-mail informado é inválido.")
    @field:Schema(description = "Endereço de e-mail corporativo para acesso ao sistema.", example = "ricardo.oliveira@webcrafters.com")
    val email: String,
)
