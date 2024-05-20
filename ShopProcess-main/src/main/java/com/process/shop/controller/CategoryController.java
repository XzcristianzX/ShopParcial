package com.process.shop.controller;

// CategoryController.java
import com.process.shop.model.Category;
import com.process.shop.model.dto.Response;
import com.process.shop.service.Category.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Response> createCategory(@RequestBody @Valid Category category) {
        categoryService.createCategory(category);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Categoria creada con éxito"))
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Category getCategoryById(@PathVariable @NotNull Long id) {
        return categoryService.getCategoryById(id);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@RequestBody @Valid Category category, @PathVariable Long id) {
        categoryService.updateCategory(category, id);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Categoria actualizada con éxito"))
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
