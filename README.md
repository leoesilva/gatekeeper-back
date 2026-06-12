# 🛡️ Gatekeeper IoT - Backend Service

![Kotlin](https://img.shields.io/badge/Kotlin-2.3.21-purple?style=flat-square&logo=kotlin)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.6-brightgreen?style=flat-square&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.4-blue?style=flat-square&logo=postgresql)
![MQTT](https://img.shields.io/badge/MQTT-Mosquitto-orange?style=flat-square)

> **Resumo Executivo**  
> O Gatekeeper é um sistema inteligente de controle de acesso (IoT) projetado para gerenciar e auditar entradas em ambientes corporativos ou residenciais através de credenciais RFID. Este repositório contém o **Core Backend**, responsável por centralizar a lógica de negócios, segurança e comunicação de toda a plataforma.

## 🏗️ Arquitetura Híbrida

O grande diferencial arquitetural do Gatekeeper reside em sua topologia bifurcada, desenhada para atender a dois domínios distintos de forma otimizada:

1.  **Interface Síncrona (RESTful API):** Através de rotas HTTP protegidas por JWT e baseadas em RBAC (Role-Based Access Control), o sistema serve dados em tempo real para o painel administrativo (Gestores e Admins) e para o aplicativo mobile (Portadores/Cardholders).
2.  **Interface Assíncrona (Event-Driven Edge):** Utilizando mensageria MQTT, o servidor se comunica com os hardwares ESP32 (Access Points) nas portas. Essa abordagem garante resiliência de rede, baixíssima latência na abertura de portas e logs de acesso confiáveis mesmo em conexões instáveis.

---

## 💻 Stack Tecnológica

*   **Linguagem:** Kotlin 2.3+
*   **Framework Principal:** Spring Boot 4.0+
*   **Persistência:** Spring Data JPA / Hibernate
*   **Banco de Dados:** PostgreSQL 18
*   **Mensageria & IoT:** Eclipse Paho MQTT v3 Client + Eclipse Mosquitto Broker
*   **Segurança:** Spring Security + JWT (`com.auth0:java-jwt`)
*   **Automação & Build:** Gradle (Kotlin DSL)

---

## 📖 Documentação das Interfaces

Mantemos a documentação das nossas interfaces sempre atualizadas como código (Doc-as-Code).

### 🌐 API REST (Swagger UI)
A documentação interativa dos endpoints HTTP, schemas e esquemas de autenticação é gerada automaticamente pelo Springdoc OpenAPI.
*   **Acesso Local:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 📨 Mensageria MQTT (AsyncAPI)
O fluxo de eventos assíncronos, formatos de payload (JSON) para telemetria (envio de logs) e comandos (abertura de portas) estão catalogados utilizando o padrão AsyncAPI.
* **Acesso Local:** [http://localhost:8080/asyncapi-ui.html](http://localhost:8080/asyncapi-ui.html)

---

## 🧪 Testes e Qualidade de Código

A qualidade é garantida através de uma suíte de testes robusta que cobre as regras de negócio críticas, especialmente os serviços de autenticação e fluxos assíncronos.

*   **Framework de Testes:** JUnit 5
*   **Mocking:** MockK (`io.mockk:mockk`)
*   **Cobertura:** JaCoCo

### Relatório de Cobertura
Para gerar e visualizar o relatório de cobertura de código do JaCoCo:
```bash
# Executa os testes e gera o relatório HTML
./gradlew test jacocoTestReport
```
*Após a execução, abra o arquivo `build/reports/jacoco/test/html/index.html` no seu navegador.*

---

## 🚀 Como Executar Localmente

Siga os passos abaixo para iniciar o ambiente de desenvolvimento completo na sua máquina.

### 1. Subir a Infraestrutura (Banco & Broker)
O projeto utiliza o Docker Compose para facilitar a orquestração do banco de dados e do servidor MQTT.

```bash
docker-compose up -d database mqtt-broker
```

### 2. Configurar Propriedades
O Spring Boot já está configurado para apontar para o `localhost` nas portas padrão expostas pelo Docker (`5432` para PostgreSQL e `1883` para MQTT). 

### 3. Executar a Aplicação
Compile e rode o servidor utilizando o wrapper do Gradle:

```bash
./gradlew bootRun
```

A API estará disponível na porta `8080` e conectada automaticamente ao broker MQTT.
