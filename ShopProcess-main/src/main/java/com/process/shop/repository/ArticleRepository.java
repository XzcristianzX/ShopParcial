package com.process.shop.repository;

import com.process.shop.model.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ArticleRepository extends CrudRepository<Article, Long> {
    Optional<Article> findByName(String name);
    // Puedes agregar métodos adicionales según tus necesidades
}
