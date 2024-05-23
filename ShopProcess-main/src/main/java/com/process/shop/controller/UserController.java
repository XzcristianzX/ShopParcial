package com.process.shop.controller;

import com.process.shop.model.User;
import com.process.shop.model.dto.Response;
import com.process.shop.model.dto.UserRequestMessages;
import com.process.shop.service.User.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Response> createUser(@RequestBody @Valid UserRequestMessages userRequest) {
        User user = userRequest.getRequestMessage().getUser();
        userService.createUser(user);
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now().toString())
                        .message("Usuario creado con éxito")
                        .statusCode(HttpStatus.CREATED.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Response> updateUser(@PathVariable Long id, @RequestBody @Valid UserRequestMessages userRequest) {
        User user = userRequest.getRequestMessage().getUser();
        if (user != null) {
            User updatedUser = userService.updateUser(user, id);
            Response response = Response.builder()
                    .responseMessage(Response.ResponseMessage.builder()
                            .date(LocalDate.now().toString())
                            .message("Usuario actualizado con éxito")
                            .statusCode(HttpStatus.OK.value())
                            .build())
                    .data(updatedUser)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("Usuario no encontrado")
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
    public ResponseEntity<Response> getAllUsers() {
        List<User> users = userService.findAllUsers();
        Response.ResponseMessage responseMessage;

        if (!users.isEmpty()) {
            responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("Lista de usuarios obtenida")
                    .statusCode(HttpStatus.OK.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .data(users)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message("No hay usuarios disponibles")
                    .statusCode(HttpStatus.NO_CONTENT.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getUserById(@PathVariable @NotNull Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
            Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message(String.valueOf(List.of("Usuario encontrado")))
                    .statusCode(HttpStatus.OK.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .data(user)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message(String.valueOf(List.of("Usuario no encontrado")))
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response> deleteUser(@PathVariable Long id) {
        User user = userService.getUserById(id);
        if (user != null) {
        userService.deleteUser(id);
        Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                .date(String.valueOf(LocalDate.now()))
                .message("Usuario eliminado exitosamente")
                .statusCode(HttpStatus.OK.value())
                .build();
        Response response = Response.builder()
                .responseMessage(responseMessage)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            Response.ResponseMessage responseMessage = Response.ResponseMessage.builder()
                    .date(String.valueOf(LocalDate.now()))
                    .message(String.valueOf(List.of("Usuario no encontrado")))
                    .statusCode(HttpStatus.NOT_FOUND.value())
                    .build();
            Response response = Response.builder()
                    .responseMessage(responseMessage)
                    .build();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }
}