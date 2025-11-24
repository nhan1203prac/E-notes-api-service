package com.Enotes_Api_Service.Enotes_Api.Config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        OpenAPI openAPI = new OpenAPI();
        Info info = new Info();
        info.setTitle("Enotes API");
        info.setVersion("1.0.0");
        info.setDescription("Enotes API");
        info.setTermsOfService("http://nhan1203prac.com");
        info.setContact(new Contact().email("vtn12032004@gmail.com")
                .name("Vo Thanh Nhan")
                .url("http://nhan1203prac.com"));
        info.setLicense(new License().name("Enotes 1.0").url("http://nhan1203prac.com"));
        List<Server> servers = List.of(new Server().description("Dev").url("http://localhost:8080"),
                new Server().description("Test").url("http://localhost:8081"),
                new Server().description("Prod").url("http://localhost:8082")
        );


        SecurityScheme securityScheme = new SecurityScheme()
                .name("Authorization")
                .scheme("bearer")
                .type(SecurityScheme.Type.HTTP)
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER);

        Components components = new Components().addSecuritySchemes("Token", securityScheme);

        openAPI.servers(servers);
        openAPI.setInfo(info);
        openAPI.setComponents(components);
        openAPI.setSecurity(List.of(new SecurityRequirement().addList("Token")));

        return openAPI;
    }
}
