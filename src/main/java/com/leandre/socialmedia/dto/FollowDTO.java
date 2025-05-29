package com.leandre.socialmedia.dto;

import lombok.Data;

public class FollowDTO {

    @Data
    public static class FollowRequest {
        private Long userId;
    }

    @Data
    public static class FollowResponse {
        private Long id;
        private Long followerId;
        private Long followedId;
    }
}