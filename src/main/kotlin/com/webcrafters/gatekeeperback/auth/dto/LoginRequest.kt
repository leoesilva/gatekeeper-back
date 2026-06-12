package com.webcrafters.gatekeeperback.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Objeto de requisição contendo as credenciais para autenticação de usuários no sistema.")
data class LoginRequest(
    @field:Email(message = "O e-mail informado é inválido.")
    @field:Schema(description = "Endereço de e-mail do usuário cadastrado.", example = "admin@gatekeeper.com")
    val email: String,

    @field:NotBlank(message = "A senha é obrigatória.")
    @field:Schema(description = "Senha de acesso do usuário.", example = "SenhaSegura@123")
    val password: String,
)
