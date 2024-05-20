package com.process.shop.model.dto;
import com.process.shop.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserRequestMessage {
    @Data
    @Builder
    public static class RequestMessage {
        private String messageID;
        @NotNull
        @Valid
        private User user;
    }
}
