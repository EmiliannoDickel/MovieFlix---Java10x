package com.dickel.movieflix.mapper;

import com.dickel.movieflix.entity.Category;
import com.dickel.movieflix.request.CategoryRequest;
import com.dickel.movieflix.response.CategoryResponse;
import lombok.experimental.UtilityClass;

@UtilityClass //nao pode ser instanciada
public class CategoryMapper {

    //Criar para receber um Request e transformar em Entity
    public static Category toCategoryRequest (CategoryRequest categoryRequest) {
        return Category.builder()
                .name(categoryRequest.name())
                .build();
    }

    //Responder uma request com uma response
    public static CategoryResponse toCategoryResponse (Category category) {
        return CategoryResponse
                .builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
