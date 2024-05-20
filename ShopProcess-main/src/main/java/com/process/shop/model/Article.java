package com.process.shop.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name should not be longer than 100 characters")
    private String name;

    @Size(max = 500, message = "Description should not be longer than 500 characters")
    private String description;

    @Min(value = 1, message = "Price must be greater than 0")
    private double price;

    @PastOrPresent(message = "Manufacture date cannot be in the future")
    private LocalDate manufactureDate;

    @PositiveOrZero(message = "Stock cannot be negative")
    private int stock;

    @PastOrPresent(message = "Created date must be in the past or present")
    private LocalDateTime createdAt;

    @PastOrPresent(message = "Updated date must be in the past or present")
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    @JsonBackReference
    private Category category;

    // Nuevos atributos


}
