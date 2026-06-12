package com.webcrafters.gatekeeperback.api.manager.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Positive

@Schema(description = "Objeto de requisição para criação de uma nova credencial RFID vinculada a um usuário.")
data class CreateRfidCredentialRequest(
    @field:NotBlank(message = "O código hexadecimal da credencial é obrigatório.")
    @field:Schema(description = "Código hexadecimal único da tag RFID.", example = "A1B2C3D4")
    val hexCode: String,

    @field:Positive(message = "O identificador do usuário deve ser maior que zero.")
    @field:Schema(description = "ID único do usuário proprietário da credencial.", example = "125")
    val appUserId: Int,
)
