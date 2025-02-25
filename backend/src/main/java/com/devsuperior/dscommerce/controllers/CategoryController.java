package com.devsuperior.dscommerce.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devsuperior.dscommerce.dto.CategoryDTO;
import com.devsuperior.dscommerce.services.CategoryService;

import io.swagger.v3.oas.annotations.Operation;

@SecurityRequirement(name = "none")
@Tag(name = "Categorias", description = "Gerencia as categorias de produtos, permitindo a criação, edição e listagem de categorias")
@RestController
@RequestMapping(value = "/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @Operation(
            summary = "Listar todas as categorias",
            description = "Este endpoint retorna todas as categorias cadastradas no sistema, permitindo ao usuário visualizar o catálogo de categorias disponíveis."
    )
    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll();
        return ResponseEntity.ok(list);
    }
}
