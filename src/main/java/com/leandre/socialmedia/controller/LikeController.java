package com.leandre.socialmedia.controller;

import com.leandre.socialmedia.dto.LikeDTO;
import com.leandre.socialmedia.repository.UserRepository;
import com.leandre.socialmedia.service.LikeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts/{postId}/likes")
@RequiredArgsConstructor
public class LikeController {
    private final LikeService likeService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<LikeDTO.LikeResponse> addLike(
            @PathVariable Long postId,
            @Valid @RequestBody LikeDTO.LikeRequest request) {
        Long userId = getCurrentUserId();
        request.setPostId(postId);
        return ResponseEntity.ok(likeService.addLike(request, userId));
    }

    @DeleteMapping
    public ResponseEntity<Void> removeLike(@PathVariable Long postId) {
        Long userId = getCurrentUserId();
        likeService.removeLike(postId, userId);
        return ResponseEntity.ok().build();
    }

    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();
    }
}