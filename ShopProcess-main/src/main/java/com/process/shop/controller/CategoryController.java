package com.process.shop.controller;

// CategoryController.java
import com.process.shop.model.Category;
import com.process.shop.model.User;
import com.process.shop.model.dto.CategoryRequestMessage;
import com.process.shop.model.dto.UserRequestMessages;
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
    public ResponseEntity<Response> createCategory(@RequestBody @Valid CategoryRequestMessage categoryRequest) {
        Category categoria = categoryRequest.getRequestMessage().getCategory();
        categoryService.createCategory(categoria);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(String.valueOf(LocalDate.now()))
                        .message("Categoría creada con éxito")
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .data(categoria)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateCategory(@PathVariable Long id, @RequestBody @Valid CategoryRequestMessage categoryRequest) {
        Category updatedCategory = categoryRequest.getRequestMessage().getCategory();
        if (updatedCategory != null) {
            Category mostrardata = updatedCategory;
            categoryService.updateCategory(updatedCategory, id);
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(LocalDate.now().toString())
                            .message("Categoría actualizada con éxito")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .data(mostrardata)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Categoría no encontrada")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getCategoryById(@PathVariable @NotNull Long id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Categoría encontrada")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .data(category)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Categoría no encontrada")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Response> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        Response.ResponseMessage responseMessage;
        HttpStatus httpStatus;

        if (!categories.isEmpty()) {
            responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("Lista de categorías obtenida")
                    .statusCode(HttpStatus.OK.value())
                    .build();
            httpStatus = HttpStatus.OK;
        } else {
            responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("No hay categorías disponibles")
                    .statusCode(HttpStatus.NO_CONTENT.value())
                    .build();
            httpStatus = HttpStatus.NO_CONTENT;
        }

        Response response = Response.builder()
                .responseMessage(responseMessage)
                .data(categories) // Incluye la lista de categorías en la respuesta
                .build();
        return new ResponseEntity<>(response, httpStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteCategory(@PathVariable Long id) {
        Category Categoria = categoryService.getCategoryById(id);
        if (Categoria != null) {
            categoryService.deleteCategory(id);
            Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("Categoría eliminada exitosamente")
                    .statusCode(HttpStatus.OK.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Error al eliminar la categoría")
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}