package com.process.shop.service.Article;
// ArticleRepository.java
import com.process.shop.model.Article;
import com.process.shop.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article createArticle(Article article) {
        article.setCreatedAt(LocalDateTime.now());
        Optional<Article> existingCategory = articleRepository.findByName(article.getName());
        if (existingCategory.isPresent()) {
            throw new RuntimeException("El articulo ya está registrado");
        }
        return articleRepository.save(article);
    }

    @Override
    public Article getArticleById(Long id) {
        Optional<Article> articleOptional = articleRepository.findById(id);
        return articleOptional.orElse(null);
    }

    @Override
    public List<Article> getAllArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    @Override
    public Article updateArticle(Article articleUpdated, Long id) {
        Optional<Article> articleBd = articleRepository.findById(id);
        if (articleBd.isEmpty()) {
            return null; // Usuario no encontrado, puedes manejarlo como desees
        }
        Article existingCategory = articleBd.get();
        existingCategory.setName(articleUpdated.getName());
        existingCategory.setDescription(articleUpdated.getDescription());
        existingCategory.setPrice(articleUpdated.getPrice());
        existingCategory.setStock(articleUpdated.getStock());
        existingCategory.setManufactureDate(articleUpdated.getManufactureDate());
        existingCategory.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(existingCategory);
    }
    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }

}