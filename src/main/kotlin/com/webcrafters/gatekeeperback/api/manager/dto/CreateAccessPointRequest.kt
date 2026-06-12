package com.webcrafters.gatekeeperback.api.manager.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Objeto de transferência de dados para a criação de um novo ponto de acesso (Access Point).")
data class CreateAccessPointRequest(
    @field:Schema(description = "Identificador único do dispositivo no broker MQTT.", example = "GATE_LAB_01")
    @field:NotBlank(message = "O identificador MQTT é obrigatório.")
    val mqttIdentifier: String,

    @field:Schema(description = "Descrição detalhada da localização física do ponto de acesso.", example = "Entrada Principal - Bloco A")
    @field:NotBlank(message = "A descrição do local é obrigatória.")
    val locationDescription: String,
)
