package com.webcrafters.gatekeeperback.messaging.subscriber

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.mockk.impl.annotations.SpyK
import com.webcrafters.gatekeeperback.domain.model.AccessPoint
import com.webcrafters.gatekeeperback.domain.repository.AccessLogRepository
import com.webcrafters.gatekeeperback.domain.repository.AccessPointRepository
import com.webcrafters.gatekeeperback.domain.repository.RfidCredentialRepository
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import org.eclipse.paho.client.mqttv3.IMqttClient
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class AccessEventSubscriberTest {

    @SpyK
    var objectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    @MockK
    private lateinit var mqttClient: IMqttClient

    @MockK
    private lateinit var accessLogRepository: AccessLogRepository

    @MockK
    private lateinit var accessPointRepository: AccessPointRepository

    @MockK
    private lateinit var rfidCredentialRepository: RfidCredentialRepository

    @InjectMockKs
    private lateinit var accessEventSubscriber: AccessEventSubscriber

    @Test
    fun `deveProcessarEventoDeAcessoComSucessoQuandoPontoDeAcessoECredencialExistem`() {
        // Arrange
        val mqttIdentifier = "GATE_01"
        val topic = "gatekeeper/access/$mqttIdentifier"
        val tagRead = "HEXCODE123"
        val timestamp = LocalDateTime.now()
        val payload = """
            {
              "tagRead": "$tagRead",
              "isGranted": true,
              "denialReason": null,
              "timestamp": "$timestamp"
            }
        """.trimIndent()

        val mockAccessPoint = AccessPoint(id = 1, mqttIdentifier = mqttIdentifier, locationDescription = "Entrada Principal")

        // Configura o comportamento dos mocks
        every { accessPointRepository.findByMqttIdentifier(mqttIdentifier) } returns mockAccessPoint
        every { rfidCredentialRepository.existsByHexCode(tagRead) } returns true
        justRun { accessLogRepository.save(any()) }

        // Act
        accessEventSubscriber.handleAccessEvent(topic, payload)

        // Assert
        verify(exactly = 1) { accessLogRepository.save(any()) }
    }

    @Test
    fun `naoDeveProcessarEventoQuandoPontoDeAcessoNaoExiste`() {
        // Arrange
        val mqttIdentifier = "GATE_UNKNOWN"
        val topic = "gatekeeper/access/$mqttIdentifier"
        val payload = """
            {
              "tagRead": "HEXCODE456",
              "isGranted": false,
              "denialReason": "INVALID_TAG",
              "timestamp": "${LocalDateTime.now()}"
            }
        """.trimIndent()

        // Configura o comportamento do mock
        every { accessPointRepository.findByMqttIdentifier(mqttIdentifier) } returns null

        // Act
        accessEventSubscriber.handleAccessEvent(topic, payload)

        // Assert
        verify(exactly = 0) { accessLogRepository.save(any()) }
        verify(exactly = 0) { rfidCredentialRepository.existsByHexCode(any()) }
    }
}
