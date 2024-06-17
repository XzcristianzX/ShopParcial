package com.process.shop.controller;

import com.process.shop.exceptions.AuthenticationFailedException;
import com.process.shop.model.dto.AuthResponse;
import com.process.shop.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
@RequiredArgsConstructor
public class AuthControler {
    private final AuthService service;

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody @Valid AuthResponse authRequest){
        try {
            AuthRequest authResponse = service.login(authRequest);
            return ResponseEntity.ok(authResponse);
        } catch (AuthenticationFailedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Authentication failed: " + e.getMessage());
        }
    }
}

