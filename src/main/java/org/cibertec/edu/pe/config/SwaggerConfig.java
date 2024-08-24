package org.cibertec.edu.pe.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@EnableSwagger2
@Configuration
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String applicatioName;

    @Value("${spring.application.description}")
    private String applicationDescription;

    @Value("${spring.application.version}")
    private String applicationVersion;

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title(applicatioName)
                        .description(applicationDescription)
                        .version(applicationVersion)
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")
                        ))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringBoot Wiki Documentation")
                        .url("https://springboot.wiki.github.org/docs"));
        /*return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.cibertec.edu.pe.rest.controllers"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(getApiInfo())
                .securitySchemes(Collections.singletonList(
                        new ApiKey(
                                "Bearer",
                                HttpHeaders.AUTHORIZATION,
                                "header"
                        )));*/
    }


    /*private ApiInfo getApiInfo() {
        return new ApiInfo(
                applicatioName,
                applicationDescription,
                applicationVersion,
                "http://example.com/terms",
                new Contact("Anonimo", "https://example.com", "apis@example.com"),
                "LICENSE",
                "LICENSE URL",
                Collections.emptyList()
        );
    }*/
}
