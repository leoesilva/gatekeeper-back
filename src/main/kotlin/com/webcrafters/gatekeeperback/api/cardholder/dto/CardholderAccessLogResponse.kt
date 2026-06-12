package com.webcrafters.gatekeeperback.api.cardholder.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Representa um registro de log de acesso de um portador de cartão no sistema.")
data class CardholderAccessLogResponse(
    @field:Schema(description = "Identificador único do log de acesso", example = "1024")
    val id: Int?,

    @field:Schema(description = "Código identificador da tag lida (UID)", example = "A1B2C3D4")
    val tagRead: String,

    @field:Schema(description = "ID do ponto de acesso onde a leitura ocorreu", example = "5")
    val accessPointId: Int?,

    @field:Schema(description = "Descrição ou nome do ponto de acesso", example = "Portaria Principal - Bloco A")
    val accessPointDescription: String,

    @field:Schema(description = "Data e hora em que o evento de acesso ocorreu", example = "2023-10-27T10:15:30")
    val timestamp: LocalDateTime,

    @field:Schema(description = "Indica se o acesso foi concedido ou negado", example = "true")
    val isGranted: Boolean,

    @field:Schema(description = "Motivo da negação, caso o acesso tenha sido recusado", example = "Cartão expirado ou sem permissão para este horário")
    val denialReason: String?,
)
