package com.portfolio.portfoliomanager.controller;

import com.portfolio.portfoliomanager.category.Category;
import com.portfolio.portfoliomanager.dto.CategoryDto;
import com.portfolio.portfoliomanager.dto.CreateCategoryRequest;
import com.portfolio.portfoliomanager.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public CategoryDto createCategory(@RequestBody CreateCategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setType(request.getType());
        Category saved = categoryService.createCategory(category);
        return mapToDto(saved);
    }

    @GetMapping
    public List<CategoryDto> getAllCategories() {
        return categoryService.getAllCategories().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private CategoryDto mapToDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        return dto;
    }

}