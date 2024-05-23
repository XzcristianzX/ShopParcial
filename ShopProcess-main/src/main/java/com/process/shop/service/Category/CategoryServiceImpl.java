package com.process.shop.service.Category;

// CategoryServiceImpl.java
import com.process.shop.model.Article;
import com.process.shop.model.Category;
import com.process.shop.repository.ArticleRepository;
import com.process.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Service

public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        // Verificar si la categoría ya existe
        Optional<Category> existingCategory = categoryRepository.findByName(category.getName());
        if (existingCategory.isPresent()) {
            // Si la categoría ya existe, no se crea una nueva
            throw new RuntimeException("La categoría ya existe.");
        }
        category.setCreatedAt(LocalDateTime.now());
        return categoryRepository.save(category);
    }

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        return categoryOptional.orElse(null);
    }

    @Override
    public List<Category> getAllCategories() {
        return (List<Category>) categoryRepository.findAll();
    }


    @Override
    public Category updateCategory(Category categoryUpdated, Long id) {
        Optional<Category> categoryBd = categoryRepository.findById(id);
        if (categoryBd.isEmpty()) {
            return null;
        }
        Optional<Category> categoryByName = categoryRepository.findByName(categoryUpdated.getName());
        if (categoryByName.isPresent() && !categoryByName.get().getId().equals(id)) {
            throw new IllegalArgumentException("La categoría ya existe con ese nombre");
        }
        Category existingCategory = categoryBd.get();
        existingCategory.setName(categoryUpdated.getName());
        existingCategory.setDescription(categoryUpdated.getDescription());
        existingCategory.setUpdatedAt(LocalDateTime.now());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(Long id) {
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isPresent()) {
            Category category = categoryOpt.get();
            if (!category.getArticle().isEmpty()) {
                throw new IllegalArgumentException("Primero borra los artículos de esta categoría");
            }
            categoryRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Categoría no encontrada");
        }
    }
}