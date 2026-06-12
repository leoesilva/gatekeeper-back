package com.webcrafters.gatekeeperback

import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest

@Disabled("Desativado temporariamente pois requer o PostgreSQL rodando para carregar o contexto. O foco atual são os testes unitários.")
@SpringBootTest
class GatekeeperBackApplicationTests {

    @Test
    fun contextLoads() {
    }

}
