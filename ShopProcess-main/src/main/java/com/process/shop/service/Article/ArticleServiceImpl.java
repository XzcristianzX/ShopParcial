package com.process.shop.service.Article;

import com.process.shop.model.Article;
import com.process.shop.repository.ArticleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    public ArticleServiceImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

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
            throw new IllegalArgumentException("El artículo no existe");
        }
        Optional<Article> articleByName = articleRepository.findByName(articleUpdated.getName());
        if (articleByName.isPresent() && !articleByName.get().getId().equals(id)) {
            throw new IllegalArgumentException("El artículo ya existe con ese nombre");
        }

        Article existingArticle = articleBd.get();
        existingArticle.setName(articleUpdated.getName());
        existingArticle.setDescription(articleUpdated.getDescription());
        existingArticle.setPrice(articleUpdated.getPrice());
        existingArticle.setStock(articleUpdated.getStock());
        existingArticle.setManufactureDate(articleUpdated.getManufactureDate());
        existingArticle.setUpdatedAt(LocalDateTime.now());
        return articleRepository.save(existingArticle);
    }

    @Override
    public void deleteArticle(Long id) {
        articleRepository.deleteById(id);
    }
}
