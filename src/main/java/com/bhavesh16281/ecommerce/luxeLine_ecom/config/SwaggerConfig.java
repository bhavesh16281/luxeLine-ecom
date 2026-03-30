package com.bhavesh16281.ecommerce.luxeLine_ecom.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .description("JWT Token");

        SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList("Bearer Authentication");

        return new OpenAPI()
                .info(new Info().title("LuxeLine-eCommerce API")
                        .version("1.0")
                        .description("API documentation for LuxeLine-eCommerce application")
                        .license(new License().name("Apache 2.0"))
                        .contact(new Contact().name("Muthyalu Bhavesh")
                                .email("bhavesh16281@gmail.com")
                                .url("https://github.com/bhavesh16281/LuxeLine-eCommerce")))
                .components(new Components()
                .addSecuritySchemes("Bearer Authentication", securityScheme))
                .addSecurityItem(securityRequirement);
    }
}
