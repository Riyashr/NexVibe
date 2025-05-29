package com.leandre.socialmedia.controller;

import com.leandre.socialmedia.dto.CommentDTO;
import com.leandre.socialmedia.repository.UserRepository;
import com.leandre.socialmedia.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<CommentDTO.CommentResponse> addComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentDTO.CommentRequest request) {
        Long userId = getCurrentUserId();
        return ResponseEntity.ok(commentService.addComment(postId, request, userId));
    }

    @GetMapping
    public ResponseEntity<List<CommentDTO.CommentResponse>> getComments(@PathVariable Long postId) {
        return ResponseEntity.ok(commentService.getCommentsForPost(postId));
    }

    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();
    }
}