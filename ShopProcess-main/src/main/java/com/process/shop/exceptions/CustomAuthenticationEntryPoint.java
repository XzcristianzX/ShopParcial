package com.process.shop.exceptions;

import com.process.shop.model.dto.Response;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {

        Response customResponse = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(String.valueOf(LocalDate.now()))
                        .message(String.valueOf(List.of("No tienes autorización: " + authException.getMessage())))
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .build())
                .build();

        response.setContentType("application/json");
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write(customResponse.toString());
    }
}
