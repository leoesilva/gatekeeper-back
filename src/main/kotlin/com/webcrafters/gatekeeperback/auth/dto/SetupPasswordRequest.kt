package com.webcrafters.gatekeeperback.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

@Schema(description = "Objeto de requisição para definição de senha de um novo usuário no sistema de controle de acesso.")
data class SetupPasswordRequest(
    @field:Email(message = "O e-mail informado é inválido.")
    @field:Schema(description = "E-mail do usuário associado à conta.", example = "operador.portaria@webcrafters.com")
    val email: String,

    @field:NotBlank(message = "A senha é obrigatória.")
    @field:Schema(description = "Nova senha a ser definida para o acesso ao sistema.", example = "SenhaSegura@2024")
    val password: String,
)
