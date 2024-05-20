package com.process.shop.controller;

import com.process.shop.model.User;
import com.process.shop.model.dto.Response;
import com.process.shop.service.User.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody @Valid User user) {
        userService.createUser(user);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Usuario creado con éxito"))
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity
                .ok()
                .body(userService.findAllUsers());
    }

    // Endpoint para obtener un usuario por su ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable @NotNull Long id) {
        return ResponseEntity
                .ok()
                .body(userService.getUserById(id));
    }


    // Endpoint para actualizar un usuario existente
    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid User user) {
        userService.updateUser(user, id);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of("Usuario actualizado con éxito"))
                        .statusCode(HttpStatus.OK.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
