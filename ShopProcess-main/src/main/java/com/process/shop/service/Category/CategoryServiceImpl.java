package com.process.shop.service.Category;

// CategoryServiceImpl.java
import com.process.shop.model.Category;
import com.process.shop.model.User;
import com.process.shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            return null; // Usuario no encontrado, puedes manejarlo como desees
        }
        Category existingCategory = categoryBd.get();
        existingCategory.setName(categoryUpdated.getName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}