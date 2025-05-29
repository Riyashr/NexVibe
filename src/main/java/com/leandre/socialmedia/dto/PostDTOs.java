package com.leandre.socialmedia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public class PostDTOs {

    @Data
    public static class PostRequest {
        @NotBlank(message = "Content is required")
        private String content;

        private String mediaUrl;

        @NotNull(message = "Post type is required")
        private String postType; // TEXT, IMAGE, VIDEO
    }

    @Data
    public static class PostResponse {
        private Long id;
        private String content;
        private String mediaUrl;
        private String postType;
        private Long userId;
        private String username;
    }
}