package com.example.simplecash.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "SimpleCash API",
                version = "1.0",
                description = "API de gestion SimpleCash: managers, agences, conseillers, clients, cartes.",
                contact = @Contact(name = "SimpleCash Support", email = "support@simplecash.local"),
                license = @License(name = "MIT")
        ),
        servers = {
                @Server(url = "/", description = "Local")
        }
)
public class OpenApiConfig {
}

