package com.process.shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
//@Builder
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Street cannot be blank")
    @Size(max = 100, message = "Street can have at most 100 characters")
    private String street;

    @Size(max = 100, message = "Avenue can have at most 100 characters")
    private String avenue;

    @NotBlank(message = "Postal Code cannot be blank")
    @Size(max = 5, message = "Postal Code must be exactly 5 digits")
    private String postalCode;

    @NotBlank(message = "Neighborhood cannot be blank")
    @Size(max = 100, message = "Neighborhood can have at most 100 characters")
    private String neighborhood;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id")
    private User user;
}
