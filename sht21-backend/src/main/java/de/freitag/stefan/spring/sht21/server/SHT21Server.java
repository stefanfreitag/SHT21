package de.freitag.stefan.spring.sht21.server;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
@EntityScan("de.freitag.stefan.spring.sht21.server")
@EnableJpaRepositories("de.freitag.stefan.spring.sht21.server")
@ComponentScan(basePackages = {"de.freitag.stefan.spring.sht21.server.api", "de.freitag.stefan.spring.sht21.server"})
public class SHT21Server implements CommandLineRunner {

    @Bean
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(javaTimeModule);
        return objectMapper;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... arg0) {
        if (arg0.length > 0 && arg0[0].equals("exitcode")) {
            throw new ExitException();
        }
    }

    public static void main(String[] args) {
        new SpringApplication(SHT21Server.class).run(args);
    }

    class ExitException extends RuntimeException implements ExitCodeGenerator {
        private static final long serialVersionUID = 1L;

        @Override
        public int getExitCode() {
            return 10;
        }
    }

    @EnableSwagger2
    @Configuration
    public class SwaggerConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .select()
                    .apis(RequestHandlerSelectors.basePackage("de.freitag.stefan.spring.sht21.server.api"))
                    .paths(PathSelectors.any())
                    .build().apiInfo(metaData());
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
    }

}
