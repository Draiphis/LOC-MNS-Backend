package com.mns.cda.locmns.config;


import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    public static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME, createBearerScheme()))
                .info(new Info()
                        .title("API MNS Loc")
                        .description("""
                                API REST de gestion du catalogue de matériel et des demandes d'emprunt.

                                Pour les routes protégées, utilisez le bouton **Authorize** puis saisissez
                                le jeton JWT retourné par `POST /connexion`.
                                """)
                        .version("1.0.0"));
    }

    private SecurityScheme createBearerScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("Jeton JWT obtenu après authentification.");
    }
}
