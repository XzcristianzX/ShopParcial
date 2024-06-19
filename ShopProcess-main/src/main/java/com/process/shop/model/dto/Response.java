package com.process.shop.model.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class Response {
    private ResponseMessage responseMessage;
    private Object data; // Campo para los datos adicionales

    @Data
    @Builder
    public static class ResponseMessage {
        private String date;
        private String message;
        private int statusCode;
    }

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }
}
