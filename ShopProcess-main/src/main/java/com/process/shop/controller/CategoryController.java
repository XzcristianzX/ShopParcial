package com.process.shop.controller;

// CategoryController.java
import com.process.shop.model.Category;
import com.process.shop.service.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    public Category updateCategory(@RequestBody Category category, @PathVariable Long id) {
        return categoryService.updateCategory(category, id);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
