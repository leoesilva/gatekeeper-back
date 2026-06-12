package com.webcrafters.gatekeeperback.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.Contact
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun gatekeeperOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Gatekeeper IoT - API de Controle de Acesso")
                    .description("API central de gerenciamento, auditoria de logs e validação de credenciais RFID para o ecossistema Gatekeeper.")
                    .version("1.0.0")
                    .contact(
                        Contact()
                            .name("WebCrafters Support")
                            .email("suporte@webcrafters.com")
                    )
            )
    }
}