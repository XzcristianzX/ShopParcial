package com.process.shop.controller;

import com.process.shop.model.Article;
import com.process.shop.service.Article.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Article createArticle(@RequestBody Article article) {
        return articleService.createArticle(article);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PutMapping("/{id}")
    public Article updateArticle(@RequestBody Article article, @PathVariable Long id) {
        return articleService.updateArticle(article, id);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
