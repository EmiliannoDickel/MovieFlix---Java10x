package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Category> getAllCateroies() {
        return categoryService.findAll();
    }

    //PathVariable extrai o valor da variável id da URL
    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        Optional<Category> categorySearch = categoryService.findById(id);
        if (categorySearch.isPresent()) {
            return categorySearch.get();
        }
        return null;
    }

    //RequestBody converte o JSON do corpo da requisição em um objeto Java
    @PostMapping
    public Category saveCategory (@RequestBody Category category) {
        return  categoryService.saveCategory(category);
    }

    @DeleteMapping("/{id}")
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteById(id);
    }


    }
