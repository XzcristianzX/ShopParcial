package com.process.shop.model.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Builder
@Data
public class Response {
    private ResponseMessage responseMessage;

    @Data
    @Builder
    public static class ResponseMessage {
        private LocalDate date;
        private List<String> message;
        private int statusCode;
    }

}