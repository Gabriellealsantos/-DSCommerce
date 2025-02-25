package com.devsuperior.dscommerce.config.swagger;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT"
)
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .info(new Info()
                        .title("DScommerce API")
                        .version("1.0")
                        .description("Esta API permite o cadastro de usuários, produtos e categorias. "
                                + "Usuários podem realizar operações como visualização do catálogo de produtos, "
                                + "adicionar produtos ao carrinho de compras e realizar pedidos. "
                                + "Clientes podem visualizar e atualizar seus cadastros, bem como registrar pedidos. "
                                + "Administradores têm acesso a funcionalidades administrativas para gerenciar cadastros.")
                        .contact(new Contact()
                                .name("Gabriel")
                                .email("gabriel.lealsantos@hotmail.com")
                                .url("https://github.com/Gabriellealsantos"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html"))
                );
    }
}
