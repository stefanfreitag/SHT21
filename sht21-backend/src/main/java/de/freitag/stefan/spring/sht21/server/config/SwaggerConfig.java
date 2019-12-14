package de.freitag.stefan.spring.sht21.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

//@EnableSwagger2
//@Configuration

public class SwaggerConfig {
  /**  @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("de.freitag.stefan.spring.sht21.server.api"))
                .paths(PathSelectors.any())
                .build().useDefaultResponseMessages(false).apiInfo(metaData());
    }

    private ApiInfo metaData() {
        return new ApiInfo(
                "SHT21 Backend REST API",
                "",
                "0.0.2",
                "",
                new Contact("Stefan Freitag", "http://www.stefreitag.de/wp", "stefan@stefreitag.de"),
                "GNU Affero General Public License",
                "http://www.gnu.org/licenses/agpl-3.0.en.html", Collections.emptyList());
    }
  */
}
