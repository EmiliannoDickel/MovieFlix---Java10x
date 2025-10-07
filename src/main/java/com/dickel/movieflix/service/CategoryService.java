package com.dickel.movieflix.service;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category saveCategory (Category category) {
        return categoryRepository.save(category);
    }


}
