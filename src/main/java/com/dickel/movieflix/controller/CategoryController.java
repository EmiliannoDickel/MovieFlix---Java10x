package com.dickel.movieflix.controller;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/category")
@RequiredArgsConstructor //cria o construtor, sendo desnecessário o @Autowired ou o construtor normal
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<Category> getAllCateroies() {
        return categoryService.findAll();
    }

    @PostMapping
    public Category saveCategory (@RequestBody Category category) {
        return  categoryService.saveCategory(category);
    }

    }
