package marcelodev.comu_carona.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI().info(
            Info()
                .title("Comu-Carona com Spring Boot 2.5.4")
                .version("v1")
                .description(
                    "Comu-Carona com Spring Boot" +
                            "<br>Developed by: <b>Marcelo</b>"
                )
                .termsOfService("http://swagger.io/terms/")
                .license(
                    License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org")
                )
        )
    }
}