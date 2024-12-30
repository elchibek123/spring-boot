package java15.instagram.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {
    private static final String API_KEY = "Bearer Token";

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes(API_KEY, apiKeySecurityScheme()))
                .info(new Info()
                        .title("Instagram API")
                        .description("Instagram API Documentation")
                        .version("1.0.0"))
                .security(Collections.singletonList(new SecurityRequirement().addList(API_KEY)));
    }

    private SecurityScheme apiKeySecurityScheme() {
        return new SecurityScheme()
                .name("Authorization")
                .description("Add your JWT token in the format: Bearer <token>")
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");
    }
}
