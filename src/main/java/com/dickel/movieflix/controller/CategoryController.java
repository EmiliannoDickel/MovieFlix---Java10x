package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.mapper.CategoryMapper;
import com.dickel.movieflix.request.CategoryRequest;
import com.dickel.movieflix.response.CategoryResponse;
import com.dickel.movieflix.response.MovieResponse;
import com.dickel.movieflix.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor //cria o construtor, sendo desnecessário o @Autowired ou o construtor normal
@Tag(name = "Category", description = "Endpoints para gerenciamento de categorias")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista de todas as categorias disponíveis", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList());
    }

    //PathVariable extrai o valor da variável id da URL
    @GetMapping("/{id}")
    @Operation(summary = "Listar todas as categorias por ID", description = "Retorna uma lista de uma categoria específica", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "200", description = "Categoria retornada com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada", content = @Content())
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    //RequestBody converte o JSON do corpo da requisição em um objeto Java
    @PostMapping
    @Operation(summary = "Salvar uma nova categoria", description = "Endpoint para salvar uma nova cateogira no sistema.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "201", description = "Categoria salva com sucesso", content = @Content(schema = @Schema(implementation = CategoryResponse.class)))
    public ResponseEntity<CategoryResponse> saveCategory (@Valid @RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategoryRequest(request);
        Category savedCategoty = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategoty));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar categoria", description = "Endpoint para deletar uma categoria existente pelo seu ID.", security = @SecurityRequirement(name = "bearerAuth"))
    @ApiResponse(responseCode = "204", description = "Categoria deletada com sucesso", content = @Content())
    @ApiResponse(responseCode = "404" , description = "Categoria não encontrado", content = @Content())
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    }
