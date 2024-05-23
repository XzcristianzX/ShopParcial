package com.process.shop.model.dto;

import com.process.shop.model.Article;
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
public class ArticleRequestMessage {
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
        @NotNull(message = "Article is required")
        private Article article;
    }
}