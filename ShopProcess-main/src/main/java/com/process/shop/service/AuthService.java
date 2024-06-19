package com.process.shop.service;

import com.process.shop.controller.AuthRequest;
import com.process.shop.exceptions.AuthenticationFailedException;
import com.process.shop.model.User;
import com.process.shop.model.dto.AuthResponse;
import com.process.shop.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final JWTservice jwTservice;
    private final AuthenticationManager authenticationManager;

    public AuthRequest login(AuthResponse authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authRequest.getEmail(), authRequest.getPassword()
            ));
        } catch (BadCredentialsException e) {
            throw new AuthenticationFailedException("Authentication failed: Bad credentials");
        } catch (AuthenticationException e) {
            throw new AuthenticationFailedException("Authentication failed: " + e.getMessage());
        }

        Optional<User> userOptional = userRepository.findByEmail(authRequest.getEmail());

        if (userOptional.isEmpty()) {
            throw new AuthenticationFailedException("User not found");
        }

        User user = userOptional.get();
        String token = jwTservice.getToken(user);

        // Guardar el token actual en el usuario
        user.setCurrentToken(token);
        userRepository.save(user);

        return AuthRequest.builder()
                .token(token)
                .build();
    }
}
