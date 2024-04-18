package com.process.shop.service.Article;

// ArticleService.java
import com.process.shop.model.Article;
import org.springframework.stereotype.Service;

import java.util.List;

public interface ArticleService {

    Article createArticle(Article article);

    Article getArticleById(Long id);

    List<Article> getAllArticles();
    void deleteArticle(Long id);
    Article updateArticle(Article article, Long id);
}
