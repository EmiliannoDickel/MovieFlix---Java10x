package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.mapper.CategoryMapper;
import com.dickel.movieflix.request.CategoryRequest;
import com.dickel.movieflix.response.CategoryResponse;
import com.dickel.movieflix.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll().stream()
                .map(CategoryMapper::toCategoryResponse)
                .toList());
    }

    //PathVariable extrai o valor da variável id da URL
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(category -> ResponseEntity.ok(CategoryMapper.toCategoryResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    //RequestBody converte o JSON do corpo da requisição em um objeto Java
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory (@RequestBody CategoryRequest request) {
        Category newCategory = CategoryMapper.toCategoryRequest(request);
        Category savedCategoty = categoryService.save(newCategory);
        return ResponseEntity.status(HttpStatus.CREATED).body(CategoryMapper.toCategoryResponse(savedCategoty));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    }
