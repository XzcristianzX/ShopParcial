package com.process.shop.service.categoriaTest;


import com.process.shop.model.Category;
import com.process.shop.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateCategory() {
        Category category = new Category();
        category.setName("Electronics");

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.empty());
        when(categoryRepository.save(category)).thenReturn(category);

        Category result = categoryService.createCategory(category);

        assertNotNull(result);
        assertEquals("Electronics", result.getName());
        assertNotNull(result.getCreatedAt());

        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void testCreateCategoryAlreadyExists() {
        Category category = new Category();
        category.setName("Electronics");

        when(categoryRepository.findByName(category.getName())).thenReturn(Optional.of(category));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            categoryService.createCategory(category);
        });

        assertEquals("La categoría ya existe.", exception.getMessage());

        verify(categoryRepository, times(1)).findByName(category.getName());
        verify(categoryRepository, never()).save(category);
    }

    @Test
    void testGetCategoryById() {
        Long categoryId = 1L;
        Category category = new Category();
        category.setId(categoryId);
        category.setName("Electronics");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(categoryId);

        assertNotNull(result);
        assertEquals(categoryId, result.getId());
        assertEquals("Electronics", result.getName());

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetCategoryByIdNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Category result = categoryService.getCategoryById(categoryId);

        assertNull(result);

        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void testGetAllCategories() {
        Category category1 = new Category();
        category1.setName("Electronics");

        Category category2 = new Category();
        category2.setName("Home Appliances");

        List<Category> categoryList = List.of(category1, category2);

        when(categoryRepository.findAll()).thenReturn(categoryList);

        List<Category> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Electronics", result.get(0).getName());
        assertEquals("Home Appliances", result.get(1).getName());

        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void testUpdateCategory() {
        Long categoryId = 1L;
        Category existingCategory = new Category();
        existingCategory.setId(categoryId);
        existingCategory.setName("Electronics");

        Category updatedCategory = new Category();
        updatedCategory.setName("Home Appliances");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);

        Category result = categoryService.updateCategory(updatedCategory, categoryId);

        assertNotNull(result);
        assertEquals("Home Appliances", result.getName());
        assertNotNull(result.getUpdatedAt());

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).save(existingCategory);
    }

    @Test
    void testUpdateCategoryNotFound() {
        Long categoryId = 1L;
        Category updatedCategory = new Category();
        updatedCategory.setName("Home Appliances");

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        Category result = categoryService.updateCategory(updatedCategory, categoryId);

        assertNull(result);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void testDeleteCategory() {
        Long categoryId = 1L;

        doNothing().when(categoryRepository).deleteById(categoryId);

        categoryService.deleteCategory(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}