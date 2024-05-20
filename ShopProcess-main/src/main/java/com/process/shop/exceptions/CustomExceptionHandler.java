package com.process.shop.exceptions;

import com.process.shop.model.dto.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
@RestControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> notFoundExceptionHandle(NotFoundException notFoundException) {
        return new ResponseEntity<>(
                Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(LocalDate.now())
                                .message(List.of(notFoundException.getMessage()))
                                .statusCode(HttpStatus.NOT_FOUND.value())
                                .build())
                        .build(),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<Response> alreadyExistsExceptionHandle(AlreadyExistsException alreadyExistsException) {
        return new ResponseEntity<>(
                Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(LocalDate.now())
                                .message(List.of(alreadyExistsException.getMessage()))
                                .statusCode(HttpStatus.CONFLICT.value())
                                .build())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Response> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            errors.add(((FieldError) error).getField() + " - " + error.getDefaultMessage());
        });

        return new ResponseEntity<>(
                Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(LocalDate.now())
                                .message(errors)
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Response> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = "El tipo del argumento no coincide. Detalles: " + ex.getMessage();

        return new ResponseEntity<>(
                Response.builder()
                        .responseMessage(Response.ResponseMessage.builder()
                                .date(LocalDate.now())
                                .message(List.of(errorMessage))
                                .statusCode(HttpStatus.BAD_REQUEST.value())
                                .build())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Response> handleGenericException(Exception ex) {
        Response response = Response.builder()
                .responseMessage(Response.ResponseMessage.builder()
                        .date(LocalDate.now())
                        .message(List.of(ex.getMessage()))
                        .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                        .build())
                .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
