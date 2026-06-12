package com.webcrafters.gatekeeperback.api.manager.dto

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Objeto de resposta contendo os detalhes de um ponto de acesso (Access Point) no sistema.")
data class AccessPointResponse(
    @field:Schema(description = "Identificador único do ponto de acesso no banco de dados", example = "1")
    val id: Int?,

    @field:Schema(description = "Identificador único de hardware utilizado para comunicação via protocolo MQTT", example = "GATE_01_NORTH")
    val mqttIdentifier: String,

    @field:Schema(description = "Descrição textual da localização física do ponto de acesso", example = "Entrada Principal - Bloco A")
    val locationDescription: String,

    @field:Schema(description = "Indica se o ponto de acesso está em modo de manutenção, restringindo acessos normais", example = "false")
    val isUnderMaintenance: Boolean,
)
