package com.process.shop.repository;

import com.process.shop.model.Category;
import com.process.shop.model.User;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {
    Optional<Category> findByName(String name);
    // Puedes agregar métodos adicionales según tus necesidades
}
