package com.process.shop.controller;

import com.process.shop.model.Article;
import com.process.shop.model.User;
import com.process.shop.model.dto.ArticleRequestMessage;
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
    public ResponseEntity<Response> createArticle(@RequestBody @Valid ArticleRequestMessage articleRequestMessage) {
        Article article = articleRequestMessage.getRequestMessage().getArticle();
        Article createdArticle = articleService.createArticle(article);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(String.valueOf(LocalDate.now()))
                        .message("Artículo creado con éxito")
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .data(createdArticle)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getArticleById(@PathVariable @NotNull Long id) {
        Article article = articleService.getArticleById(id);
        Response response;
        if (article != null) {
            response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Artículo encontrado")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .data(article) // Incluye el artículo encontrado en la respuesta
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Artículo no encontrado")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<Response> getAllArticles() {
        List<Article> articles = articleService.getAllArticles();
        Response response;
        if (!articles.isEmpty()) {
            response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Lista de artículos obtenida")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .data(articles) // Incluye la lista de artículos en la respuesta
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("No hay artículos disponibles")
                            .statusCode(HttpStatus.NO_CONTENT.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateArticle(@PathVariable Long id, @RequestBody @Valid ArticleRequestMessage articleRequestMessage) {
        Article updatedArticle = articleRequestMessage.getRequestMessage().getArticle();
        if (updatedArticle != null) {
            try {
                Article mostrardata = updatedArticle;
                articleService.updateArticle(updatedArticle, id);
                Response response = Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(LocalDate.now().toString())
                                .message("Artículo actualizado con éxito")
                                .statusCode(HttpStatus.OK.value())
                                .build())
                        .data(mostrardata)
                        .build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                Response response = Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(String.valueOf(LocalDate.now()))
                                .message(e.getMessage())
                                .statusCode(HttpStatus.CONFLICT.value())
                                .build())
                        .build();
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }
        } else {
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Artículo no encontrado")
                            .statusCode(HttpStatus.NOT_FOUND.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteArticle(@PathVariable Long id) {
        Article article= articleService.getArticleById(id);
        if (article !=null){
            articleService.deleteArticle(id);
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Artículo eliminado exitosamente")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        }else{
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(String.valueOf(LocalDate.now()))
                            .message("Error al eliminar el artículo")
                            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                            .build())
                    .build();
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}