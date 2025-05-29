package com.leandre.socialmedia.dto;

import lombok.Data;

public class LikeDTO {

    @Data
    public static class LikeRequest {
        private Long postId;
    }

    @Data
    public static class LikeResponse {
        private Long id;
        private Long userId;
        private Long postId;
    }
}