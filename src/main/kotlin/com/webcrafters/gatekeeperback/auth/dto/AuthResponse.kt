package com.webcrafters.gatekeeperback.auth.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Objeto de resposta para operações de autenticação e autorização.")
data class AuthResponse(
    @field:Schema(description = "Mensagem informativa sobre o status da operação.", example = "Autenticação realizada com sucesso.")
    val message: String,

    @field:Schema(description = "Token JWT para acesso aos endpoints protegidos do sistema de controle de acesso.", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    val token: String? = null,
)
