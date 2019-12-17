package de.freitag.stefan.spring.sht21.server.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new Info()
                        .title("SHT21 Backend REST API")
                        .contact(
                                new Contact().name("Stefan Freitag")
                                        .url("http://www.stefreitag.de/wp")
                                        .email("stefan@stefreitag.de")
                        ).license(new License().name("GNU Affero General Public License"))
                );

    }

}

