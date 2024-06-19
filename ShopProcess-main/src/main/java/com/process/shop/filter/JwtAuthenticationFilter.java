package com.process.shop.filter;

import com.process.shop.model.User;
import com.process.shop.repository.UserRepository;
import com.process.shop.service.JWTservice;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTservice jwTservice;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            String token = bearerToken.replace("Bearer ", "");
            String userEmail = jwTservice.getEmailFromToken(token);
            if (userEmail != null) {
                User user = userRepository.findByEmail(userEmail).orElse(null);
                if (user != null && token.equals(user.getCurrentToken())) {
                    UsernamePasswordAuthenticationToken usernameToken = JWTservice.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(usernameToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
