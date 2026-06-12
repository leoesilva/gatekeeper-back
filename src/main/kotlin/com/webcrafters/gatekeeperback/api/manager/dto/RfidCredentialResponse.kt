package com.webcrafters.gatekeeperback.api.manager.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Objeto de resposta contendo os detalhes de uma credencial RFID cadastrada.")
data class RfidCredentialResponse(
    @field:Schema(description = "Identificador único da credencial no banco de dados", example = "1")
    val id: Int?,

    @field:Schema(description = "Código hexadecimal único da tag RFID", example = "A1B2C3D4")
    val hexCode: String,

    @field:Schema(description = "ID do usuário associado a esta credencial", example = "42")
    val appUserId: Int?,

    @field:Schema(description = "Indica se a credencial está bloqueada para acesso", example = "false")
    val isBlocked: Boolean,
)
