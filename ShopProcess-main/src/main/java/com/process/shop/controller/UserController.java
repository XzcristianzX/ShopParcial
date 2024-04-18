package com.process.shop.controller;

import com.process.shop.model.User;
import com.process.shop.service.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;

    // Endpoint para crear un nuevo usuario
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Endpoint para obtener todos los usuarios
    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    // Endpoint para obtener un usuario por su ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }


    // Endpoint para actualizar un usuario existente
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(user,id);
    }

    // Endpoint para eliminar un usuario por su ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}
