# Gatekeeper (Módulo Backend)

![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white)
![MQTT](https://img.shields.io/badge/MQTT-660066?style=for-the-badge&logo=mqtt&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

---

## 1. Nome do Projeto: Gatekeeper (Módulo Backend)
Este documento detalha a implementação do componente de backend para o projeto Gatekeeper, responsável pela lógica de negócio, segurança e comunicação com dispositivos IoT.

## 2. Integrantes:
- Caio Cesar Silva Pena
- Leonardo Euripedes da Silva

## 3. Tema Escolhido: Sistema Híbrido de Controle de Acesso IoT
O projeto implementa um sistema de controle de acesso físico que combina tecnologias de IoT, Edge Computing e uma arquitetura de software híbrida para oferecer uma solução robusta, resiliente e segura.

## 4. Descrição do Problema Resolvido:
O Gatekeeper foi projetado para modernizar e resolver as fragilidades dos sistemas de controle de acesso convencionais. A solução aborda os seguintes pontos-chave:

- **Orquestração de Hardware na Borda (Edge Computing):** A lógica de validação de credenciais é distribuída para os dispositivos de hardware (ESP32). Isso garante que a operação de abertura de portas seja instantânea, sem depender de uma consulta em tempo real ao servidor central.
- **Funcionamento Offline:** Cada dispositivo de acesso na borda mantém um cache local de credenciais autorizadas. Caso o servidor principal ou a conexão de rede fiquem indisponíveis, os dispositivos continuam operando de forma autônoma, garantindo o acesso para usuários já validados sem interrupções.
- **Arquitetura Híbrida e Assíncrona:** O sistema utiliza duas formas de comunicação para máxima eficiência:
    - **MQTT (Message Queuing Telemetry Transport):** Para comunicação assíncrona e de baixo overhead entre o backend e os dispositivos de hardware. Eventos como atualização de cache, logs de acesso e comandos de manutenção são trafegados via broker MQTT (Mosquitto).
    - **API REST:** Para a interface de gerenciamento (painel web/mobile). Fornece endpoints seguros para administradores e gerentes de condomínio realizarem o cadastro de usuários, credenciais, pontos de acesso e visualizarem relatórios.

## 5. Lista de Entidades Implementadas:
O domínio da aplicação foi modelado com base nas seguintes entidades principais:

1.  **`AppUser`**: Representa um usuário no sistema, seja ele um Administrador, Gerente ou Portador de credencial. Contém informações como nome, e-mail, senha e papel (role).
2.  **`AccessPoint`**: Modela o dispositivo de hardware (ESP32) instalado em um local físico (ex: "Portaria Principal"). Contém um identificador único para comunicação MQTT e sua descrição.
3.  **`RfidCredential`**: Representa um cartão ou tag RFID físico. Está associado a um `AppUser` e possui um código hexadecimal único que é lido pelo `AccessPoint`.
4.  **`AccessLog`**: Um registro imutável de todas as tentativas de acesso. Grava qual tag foi usada, em qual ponto de acesso, o horário e se o acesso foi concedido ou negado.
5.  **`OneTimePassword`**: Entidade para armazenar códigos de uso único (OTP) gerados para o processo de redefinição de senha, garantindo a segurança da troca de credenciais.

## 6. Instruções para Execução:
Para executar o ambiente completo (Backend, Banco de Dados e Broker MQTT), siga os passos abaixo.

> **Nota:** A documentação completa e interativa dos endpoints da API está disponível via Swagger UI. Após iniciar a aplicação, acesse: [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

**Passo 1: Iniciar a Infraestrutura com Docker**

O `docker-compose.yml` na raiz do projeto orquestra os contêineres para o banco de dados PostgreSQL e o broker Mosquitto. Certifique-se de ter o Docker e o Docker Compose instalados e execute o comando:

```bash
# Inicia os contêineres em modo detached (background)
docker-compose up -d
```

**Passo 2: Iniciar a Aplicação Spring Boot**

Com a infraestrutura rodando, inicie a API do backend utilizando o Gradle Wrapper fornecido.

```bash
# No Linux ou macOS
./gradlew bootRun

# No Windows
gradlew.bat bootRun
```

A API estará disponível na porta `8080`.

## 7. Variáveis de Ambiente Necessárias:
Para que a aplicação funcione corretamente, é preciso criar um arquivo `.env` na raiz do projeto (ou `.env.development` para execução local) com base no `.env.example`. As seguintes variáveis são essenciais:

```ini
# === Configuração do Banco de Dados PostgreSQL ===
DB_NAME=gatekeeper_db
DB_USERNAME=admin
DB_PASSWORD=adminpassword
DB_URL="jdbc:postgresql://localhost:5432/gatekeeper_db" # URL para execução local

# === Configuração do Broker MQTT Mosquitto ===
MQTT_BROKER_URL=tcp://localhost:1883 # URL para execução local
MQTT_CLIENT_ID=gatekeeper-backend-spring

# === Configuração de Segurança JWT ===
JWT_SECRET=GatekeeperBackJwtSecretKey_ChangeInProduction_32CharsMin
JWT_EXPIRATION_MS=86400000 # 24 horas
```

## 8. Exemplos de Usuários/Senhas para Teste:
Para facilitar a avaliação do projeto, o sistema é inicializado com usuários de teste para cada perfil de acesso. Utilize as credenciais abaixo para autenticar-se no Swagger e testar os endpoints protegidos correspondentes a cada papel.

- **Perfil Administrador:**
    - **Usuário:** `admin@gatekeeper.com`
    - **Senha:** `admin123`
    - **Nota:** Com este usuário, é possível gerenciar outros administradores e criar usuários do tipo `MANAGER`.

- **Perfil Gerente:**
    - **Usuário:** `manager@gatekeeper.com`
    - **Senha:** `manager123`
    - **Nota:** Com este usuário, é possível gerenciar usuários do tipo `CARDHOLDER`, credenciais RFID e visualizar logs de acesso.

- **Perfil Portador (Usuário Final):**
    - **Usuário:** `user@gatekeeper.com`
    - **Senha:** `user123`
    - **Nota:** Este usuário pode visualizar seus próprios dados e logs de acesso.

## 9. Divisão de Responsabilidades por Integrante:
A colaboração no projeto foi estruturada da seguinte forma:

- **Leonardo Euripedes da Silva:** Responsável integral pela implementação técnica e desenvolvimento do software backend. Isso incluiu a codificação em Kotlin, configuração do Spring Boot, implementação da camada de segurança com Spring Security e JWT, integração com banco de dados PostgreSQL via JPA/Hibernate, desenvolvimento de toda a lógica de comunicação com o broker Mosquitto (MQTT), criação dos CRUDs, serviços, DTOs e a arquitetura de sincronização de cache com os dispositivos de borda.

- **Caio Cesar Silva Pena:** Participou ativamente das etapas de concepção e planejamento da arquitetura do sistema. Colaborou na definição do escopo, no desenho inicial das entidades, na discussão sobre a escolha das tecnologias (arquitetura híbrida REST/MQTT) e na validação dos requisitos funcionais e não-funcionais do projeto.
