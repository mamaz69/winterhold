package com.winterhold;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI winterholdOpenAPI(){
        String schemaName = "bearerAuth";

        Info info = new Info().title("Winterhold API Documentation")
                .description("Ujian Spring Winterhold.")
                .version("v 1.0.0")
                .license(new License().name("Apache 2.0").url("http://springdoc.org"));

        ExternalDocumentation externalDocumentation = new ExternalDocumentation()
                .description("Winterhold MVC UI")
                .url("/winterhold");

        SecurityRequirement requirement = new SecurityRequirement().addList(schemaName);
        SecurityScheme scheme = new SecurityScheme()
                .name(schemaName)
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT");

        Components components = new Components().addSecuritySchemes(schemaName,scheme);

        OpenAPI openAPI = new OpenAPI()
                .info(info)
                .externalDocs(externalDocumentation)
                .addSecurityItem(requirement)
                .components(components);

        return  openAPI;
    }

}
