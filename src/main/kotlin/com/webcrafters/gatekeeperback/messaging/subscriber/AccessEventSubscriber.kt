package com.webcrafters.gatekeeperback.messaging.subscriber

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.json.JsonMapper
import com.webcrafters.gatekeeperback.domain.model.AccessLog
import com.webcrafters.gatekeeperback.domain.repository.AccessLogRepository
import com.webcrafters.gatekeeperback.domain.repository.AccessPointRepository
import com.webcrafters.gatekeeperback.domain.repository.RfidCredentialRepository
import jakarta.annotation.PostConstruct
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class AccessEventSubscriber(
    private val mqttClient: IMqttClient,
    private val accessLogRepository: AccessLogRepository,
    private val accessPointRepository: AccessPointRepository,
    private val rfidCredentialRepository: RfidCredentialRepository, // Adicionado para validação
) {
    private val logger = LoggerFactory.getLogger(AccessEventSubscriber::class.java)
    private val objectMapper: ObjectMapper = JsonMapper.builder().findAndAddModules().build()

    @PostConstruct
    fun subscribe() {
        try {
            mqttClient.subscribe("gatekeeper/access/+") { topic, message ->
                handleAccessEvent(topic, String(message.payload))
            }
            logger.info("✅ Inscrito no tópico gatekeeper/access/+")
        } catch (e: Exception) {
            logger.error("❌ Erro ao se inscrever no tópico MQTT", e)
        }
    }

    internal fun handleAccessEvent(topic: String, payload: String) {
        try {
            val mqttIdentifier = topic.substringAfter("gatekeeper/access/")
            val accessPoint = accessPointRepository.findByMqttIdentifier(mqttIdentifier)
                ?: run {
                    logger.warn("Ponto de acesso não encontrado: $mqttIdentifier")
                    return
                }

            val event = objectMapper.readValue(payload, AccessEventPayload::class.java)

            // Validação: Verificar se a credencial (tag) existe no banco de dados.
            if (!rfidCredentialRepository.existsByHexCode(event.tagRead)) {
                logger.warn("Credencial RFID desconhecida recebida: ${event.tagRead}. O log não será salvo.")
                return
            }

            val accessLog = AccessLog(
                tagRead = event.tagRead,
                accessPoint = accessPoint,
                timestamp = event.timestamp, // Usar o timestamp do payload
                isGranted = event.isGranted,
                denialReason = event.denialReason,
            )
            accessLogRepository.save(accessLog)

            logger.info("📝 AccessLog criado: tag=${event.tagRead}, ponto=$mqttIdentifier, concedido=${event.isGranted}")
        } catch (e: Exception) {
            logger.error("❌ Erro ao processar evento de acesso do tópico $topic. Payload: $payload", e)
        }
    }

    /**
     * DTO para o payload do evento de acesso.
     * Exemplo de JSON esperado:
     * {
     *   "tagRead": "ABC123EF",
     *   "isGranted": true,
     *   "denialReason": null,
     *   "timestamp": "2024-05-21T10:00:00"
     * }
     */
    data class AccessEventPayload(
        @JsonProperty("tagRead") val tagRead: String,
        @JsonProperty("isGranted") val isGranted: Boolean,
        @JsonProperty("denialReason") val denialReason: String?,
        @JsonProperty("timestamp") val timestamp: LocalDateTime,
    )
}
