package org.cibertec.edu.pe.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@EnableSwagger2
@Configuration
public class SwaggerConfig {
    @Value("${spring.application.name}")
    private String applicatioName;

    @Value("${spring.application.description}")
    private String applicationDescription;

    @Value("${spring.application.version}")
    private String applicationVersion;

    @Bean
    public Docket apiDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
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
                        )));
    }


    private ApiInfo getApiInfo() {
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
    }
}
