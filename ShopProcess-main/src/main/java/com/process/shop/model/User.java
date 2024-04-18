package com.process.shop.model;

import com.process.shop.model.enunm.DocumentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private DocumentType ccType;
    private String cc;
    private LocalDate birthDay;
    private String phoneNumber;
    private String email;
    private String password;

    @OneToMany(mappedBy = "user")
    private List<Address> address;


}
