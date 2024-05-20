package com.process.shop.model;

import com.process.shop.model.enunm.DocumentType;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Please provide the full name")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @NotNull(message = "Please provide the document type")
    private DocumentType ccType;

    @NotBlank(message = "Please provide the full document")
    @Size(min = 5, max = 20, message = "Document must be between 5 and 20 characters")
    private String cc;

    @NotNull(message = "Please provide the birth date")
    private LocalDate birthDay;

    @NotBlank(message = "Please provide the phone number")
    @Min(value = 10,message = "Phone number must be a valid format")
    private String phoneNumber;

    @NotBlank(message = "Please provide the email")
    @Email(message = "Please provide a valid email address")
    private String email;

    @NotBlank(message = "Please provide the password")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters")
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Address> address;
}
