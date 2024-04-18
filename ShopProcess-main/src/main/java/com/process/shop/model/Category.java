package com.process.shop.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Article> article;

    /*@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
    private List<Article> articles;*/
}