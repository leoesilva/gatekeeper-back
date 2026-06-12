package com.webcrafters.gatekeeperback.api.admin.dto

import com.webcrafters.gatekeeperback.domain.model.Role
import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Objeto de resposta contendo os detalhes do usuário do sistema.")
data class AppUserResponse(
    @field:Schema(description = "Identificador único do usuário", example = "1")
    val id: Int?,

    @field:Schema(description = "Nome completo do usuário", example = "João Silva")
    val fullName: String,

    @field:Schema(description = "Endereço de e-mail institucional", example = "joao.silva@webcrafters.com")
    val email: String,

    @field:Schema(description = "Papel ou nível de acesso do usuário no sistema", example = "ADMIN")
    val role: Role,

    @field:Schema(description = "Indica se a conta do usuário está ativa para acesso", example = "true")
    val isActive: Boolean,
)
