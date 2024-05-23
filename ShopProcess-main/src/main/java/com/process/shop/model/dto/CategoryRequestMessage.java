package com.process.shop.model.dto;

import com.process.shop.model.Category;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryRequestMessage {
    @Valid
    @NotNull(message = "RequestMessage is required")
    private RequestMessage requestMessage;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class RequestMessage {
        @NotNull(message = "MessageID is required")
        private String messageID;

        @Valid
        @NotNull(message = "Category is required")
        private Category category;
    }
}