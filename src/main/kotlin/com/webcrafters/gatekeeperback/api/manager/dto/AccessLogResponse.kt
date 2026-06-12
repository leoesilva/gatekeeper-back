package com.webcrafters.gatekeeperback.api.manager.dto

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime

@Schema(description = "Representa um registro de log de acesso processado pelo sistema.")
data class AccessLogResponse(
    @field:Schema(description = "Identificador único do log no banco de dados", example = "1024")
    val id: Int?,

    @field:Schema(description = "Código identificador da tag RFID lida", example = "A1B2C3D4")
    val tagRead: String,

    @field:Schema(description = "ID do ponto de acesso (hardware) onde a leitura ocorreu", example = "1")
    val accessPointId: Int?,

    @field:Schema(description = "Data e hora exata do evento de acesso", example = "2023-10-27T10:15:30")
    val timestamp: LocalDateTime,

    @field:Schema(description = "Indica se o acesso foi permitido ou negado", example = "true")
    val isGranted: Boolean,

    @field:Schema(description = "Motivo detalhado em caso de acesso negado", example = "Tag não cadastrada ou sem permissão para este horário")
    val denialReason: String?,
)
