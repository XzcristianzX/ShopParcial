package com.process.shop.service.articleTest;


import com.process.shop.model.Article;
import com.process.shop.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ArticleServiceImplTest {

    @Mock
    private ArticleRepository articleRepository;

    @InjectMocks
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateArticle() {
        Article article = new Article();
        article.setName("Laptop"); // Se establece el nombre del artículo correctamente
        article.setDescription("High-end gaming laptop");
        article.setPrice(1500.00);
        article.setManufactureDate(LocalDate.of(2023, 5, 22));
        article.setStock(10);

        when(articleRepository.save(article)).thenReturn(article);

        Article result = articleService.createArticle(article);

        assertNotNull(result);
        assertEquals("Laptop", result.getName()); // Se espera que el nombre sea "Laptop"
        assertEquals("High-end gaming laptop", result.getDescription());
        assertEquals(1500.00, result.getPrice());
        assertEquals(LocalDate.of(2023, 5, 22), result.getManufactureDate());
        assertEquals(10, result.getStock());
        assertNotNull(result.getCreatedAt());

        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void testGetArticleById() {
        Long articleId = 1L;
        Article article = new Article();
        article.setId(articleId);
        article.setName("Laptop");

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(articleId);

        assertNotNull(result);
        assertEquals(articleId, result.getId());
        assertEquals("Laptop", result.getName());

        verify(articleRepository, times(1)).findById(articleId);
    }

    @Test
    void testGetArticleByIdNotFound() {
        Long articleId = 1L;

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        Article result = articleService.getArticleById(articleId);

        assertNull(result);

        verify(articleRepository, times(1)).findById(articleId);
    }

    @Test
    void testGetAllArticles() {
        Article article1 = new Article();
        article1.setName("Laptop");

        Article article2 = new Article();
        article2.setName("Smartphone");

        List<Article> articleList = List.of(article1, article2);

        when(articleRepository.findAll()).thenReturn(articleList);

        List<Article> result = articleService.getAllArticles();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Laptop", result.get(0).getName());
        assertEquals("Smartphone", result.get(1).getName());

        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void testUpdateArticle() {
        Long articleId = 1L;
        Article existingArticle = new Article();
        existingArticle.setId(articleId);
        existingArticle.setName("Laptop");

        Article updatedArticle = new Article();
        updatedArticle.setName("Gaming Laptop");
        updatedArticle.setDescription("High-end gaming laptop with enhanced features");
        updatedArticle.setPrice(2000.00);
        updatedArticle.setManufactureDate(LocalDate.of(2024, 1, 10));
        updatedArticle.setStock(5);

        when(articleRepository.findById(articleId)).thenReturn(Optional.of(existingArticle));
        when(articleRepository.save(existingArticle)).thenReturn(existingArticle);

        Article result = articleService.updateArticle(updatedArticle, articleId);

        assertNotNull(result);
        assertEquals("Gaming Laptop", result.getName());
        assertEquals("High-end gaming laptop with enhanced features", result.getDescription());
        assertEquals(2000.00, result.getPrice());
        assertEquals(LocalDate.of(2024, 1, 10), result.getManufactureDate());
        assertEquals(5, result.getStock());
        assertNotNull(result.getUpdatedAt());

        verify(articleRepository, times(1)).findById(articleId);
        verify(articleRepository, times(1)).save(existingArticle);
    }

    @Test
    void testUpdateArticleNotFound() {
        Long articleId = 1L;
        Article updatedArticle = new Article();
        updatedArticle.setName("Gaming Laptop");

        when(articleRepository.findById(articleId)).thenReturn(Optional.empty());

        Article result = articleService.updateArticle(updatedArticle, articleId);

        assertNull(result);

        verify(articleRepository, times(1)).findById(articleId);
        verify(articleRepository, never()).save(any(Article.class));
    }

    @Test
    void testDeleteArticle() {
        Long articleId = 1L;

        doNothing().when(articleRepository).deleteById(articleId);

        articleService.deleteArticle(articleId);

        verify(articleRepository, times(1)).deleteById(articleId);
    }
}
