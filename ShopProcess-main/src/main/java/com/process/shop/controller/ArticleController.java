package com.process.shop.controller;

import com.process.shop.model.Article;
import com.process.shop.model.dto.Response;
import com.process.shop.service.Article.ArticleService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseEntity<Response> createArticle(@RequestBody @Valid Article article) {
        articleService.createArticle(article);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Articulo creado con éxito"))
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable @NotNull Long id) {
        return articleService.getArticleById(id);
    }

    @GetMapping
    public List<Article> getAllArticles() {
        return articleService.getAllArticles();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response>  updateArticle(@RequestBody @Valid Article article, @PathVariable Long id) {
        articleService.updateArticle(article, id);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Articulo actualizado con éxito"))
                        .statusCode(HttpStatus.OK.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        articleService.deleteArticle(id);
    }
}
