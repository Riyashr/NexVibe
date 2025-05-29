package com.leandre.socialmedia.controller;

import com.leandre.socialmedia.dto.FollowDTO;
import com.leandre.socialmedia.dto.UserDTOs;
import com.leandre.socialmedia.repository.UserRepository;
import com.leandre.socialmedia.service.FollowService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class FollowController {
    private final FollowService followService;
    private final UserRepository userRepository;

    @PostMapping("/{userId}/follow")
    public ResponseEntity<FollowDTO.FollowResponse> followUser(
            @PathVariable Long userId,
            @Valid @RequestBody FollowDTO.FollowRequest request) {
        Long followerId = getCurrentUserId();
        request.setUserId(userId);
        return ResponseEntity.ok(followService.followUser(request, followerId));
    }

    @DeleteMapping("/{userId}/unfollow")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId) {
        Long followerId = getCurrentUserId();
        followService.unfollowUser(userId, followerId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/me/following")
    public ResponseEntity<List<UserDTOs.UserResponse>> getFollowing() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(followService.getFollowing(userId));
    }

    @GetMapping("/me/followers")
    public ResponseEntity<List<UserDTOs.UserResponse>> getFollowers() {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(followService.getFollowers(userId));
    }

    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();
    }
}