package com.webcrafters.gatekeeperback.auth.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

@Schema(description = "Objeto de requisição para validação de OTP e definição de nova senha de acesso.")
data class ValidateOtpRequest(
    @field:NotBlank(message = "O código OTP é obrigatório.")
    @field:Schema(description = "Código de verificação enviado ao e-mail do usuário.", example = "123456")
    val code: String,

    @field:NotBlank(message = "A senha é obrigatória.")
    @field:Schema(description = "Nova senha a ser definida para a conta.", example = "SenhaSegura@2024")
    val password: String,

    @field:NotBlank(message = "O e-mail é obrigatório.")
    @field:Schema(description = "E-mail do usuário associado à solicitação de recuperação.", example = "usuario@webcrafters.com")
    val email: String,
)
