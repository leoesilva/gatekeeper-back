package com.webcrafters.gatekeeperback.messaging.publisher

import com.webcrafters.gatekeeperback.core.config.MqttConfig
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class AccessCommandPublisher(private val mqttGateway: MqttConfig.MqttGateway) {

    private val logger = LoggerFactory.getLogger(AccessCommandPublisher::class.java)

    fun grantAccess(mqttIdentifier: String) {
        val topic = "gatekeeper/command/$mqttIdentifier"
        val payload = """{"command":"GRANT_ACCESS"}"""
        try {
            mqttGateway.sendToMqtt(topic, payload)
            logger.info("✅ Comando GRANT_ACCESS enviado para o tópico: $topic")
        } catch (e: Exception) {
            logger.error("❌ Erro ao enviar comando MQTT para o tópico: $topic", e)
        }
    }
}
