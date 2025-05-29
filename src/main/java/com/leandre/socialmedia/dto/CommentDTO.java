package com.leandre.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class CommentDTO {

    @Data
    public static class CommentRequest {
        @NotBlank(message = "Content is required")
        private String content;
    }

    @Data
    public static class CommentResponse {
        private Long id;
        private String content;
        private Long userId;
        private String username;
        private Long postId;
    }
}