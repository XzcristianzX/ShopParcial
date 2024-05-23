package com.process.shop.service.Article;

import com.process.shop.model.Article;
import com.process.shop.repository.ArticleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ArticleServiceImplTest {
    private ArticleRepository articleRepository;
    private ArticleServiceImpl articleService;

    @BeforeEach
    void setUp() {
        articleRepository = mock(ArticleRepository.class);
        articleService = new ArticleServiceImpl(articleRepository);
    }

    @Test
    void createArticle() {
        Article article = new Article();
        article.setName("New Article");

        when(articleRepository.findByName("New Article")).thenReturn(Optional.empty());
        when(articleRepository.save(article)).thenReturn(article);

        Article result = articleService.createArticle(article);

        assertNotNull(result);
        assertEquals("New Article", result.getName());
        verify(articleRepository, times(1)).save(article);
    }

    @Test
    void getArticleById() {
        Article article = new Article();
        article.setId(1L);
        article.setName("Existing Article");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(article));

        Article result = articleService.getArticleById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(articleRepository, times(1)).findById(1L);
    }

    @Test
    void getAllArticles() {
        Article article1 = new Article();
        article1.setId(1L);
        article1.setName("Article 1");

        Article article2 = new Article();
        article2.setId(2L);
        article2.setName("Article 2");

        List<Article> articles = Arrays.asList(article1, article2);

        when(articleRepository.findAll()).thenReturn(articles);

        List<Article> result = articleService.getAllArticles();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(articleRepository, times(1)).findAll();
    }

    @Test
    void updateArticle() {
        Article existingArticle = new Article();
        existingArticle.setId(1L);
        existingArticle.setName("Existing Article");

        Article updatedArticle = new Article();
        updatedArticle.setName("Updated Article");

        when(articleRepository.findById(1L)).thenReturn(Optional.of(existingArticle));
        when(articleRepository.findByName("Updated Article")).thenReturn(Optional.empty());
        when(articleRepository.save(existingArticle)).thenReturn(existingArticle);

        Article result = articleService.updateArticle(updatedArticle, 1L);

        assertNotNull(result);
        assertEquals("Updated Article", result.getName());
        verify(articleRepository, times(1)).save(existingArticle);
    }

    @Test
    void deleteArticle() {
        doNothing().when(articleRepository).deleteById(1L);

        articleService.deleteArticle(1L);

        verify(articleRepository, times(1)).deleteById(1L);
    }
}