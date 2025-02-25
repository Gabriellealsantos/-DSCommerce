package com.devsuperior.dscommerce.controllers;

import java.net.URI;

import javax.validation.Valid;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.devsuperior.dscommerce.dto.OrderDTO;
import com.devsuperior.dscommerce.services.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;

@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Pedidos", description = "Gerencia os pedidos realizados pelos usuários autenticados, incluindo visualização e criação de novos pedidos")
@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Operation(
            summary = "Buscar pedido por ID",
            description = "Este endpoint permite que um cliente ou administrador busque um pedido específico pelo seu ID."
    )
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_CLIENT')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderDTO> findById(@Parameter(description = "ID do pedido a ser buscado") @PathVariable Long id) {
        OrderDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @Operation(
            summary = "Registrar um novo pedido",
            description = "Este endpoint permite que apenas um cliente logado registre um novo pedido. O pedido é criado com status 'aguardando pagamento'.",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            examples = @ExampleObject(
                                    name = "Exemplo de Pedido",
                                    summary = "Exemplo de dados para registrar um pedido",
                                    value = """
                                            {
                                                "items": [
                                                    {
                                                        "productId": 1,
                                                        "quantity": 2
                                                    },
                                                    {
                                                        "productId": 5,
                                                        "quantity": 1
                                                    }
                                                ]
                                            }
                                            """
                            )
                    )
            )
    )
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    @PostMapping
    public ResponseEntity<OrderDTO> insert(@Valid @RequestBody OrderDTO dto) {
        dto = service.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }
}
