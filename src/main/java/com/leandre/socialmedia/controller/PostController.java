package com.leandre.socialmedia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leandre.socialmedia.dto.PostDTOs;
import com.leandre.socialmedia.repository.UserRepository;
import com.leandre.socialmedia.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserRepository userRepository;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PostDTOs.PostResponse> createPost(
            @Valid @RequestBody PostDTOs.PostRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file) throws IOException {

        Long userId = getCurrentUserId();
        return ResponseEntity.ok(postService.createPost(request, file, userId));
    }

    @GetMapping
    public ResponseEntity<List<PostDTOs.PostResponse>> getAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDTOs.PostResponse> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        postService.deletePost(id, userId);
        return ResponseEntity.ok().build();
    }

    private Long getCurrentUserId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found")).getId();
    }
}