package com.dickel.movieflix.config;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth",
        type = io.swagger.v3.oas.annotations.enums.SecuritySchemeType.HTTP,
        scheme = "bearer") // Define o esquema de segurança como Bearer Token
public class SwaggerConfig {
    @Bean
    public OpenAPI getOpenAPI() {
        Contact contact = new Contact();
        contact.setUrl("https://github.com/EmiliannoDickel");
        contact.name("EmiliannoDickel");

        Info info = new Info()
                .title("MovieFlix API")
                .description("API para gerenciamento de filmes, categorias e serviços de streaming.")
                .version("v1")
                .contact(contact);
                return new OpenAPI().info(info);
    }
}


