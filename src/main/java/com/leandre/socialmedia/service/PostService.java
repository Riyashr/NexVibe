package com.leandre.socialmedia.service;

import com.leandre.socialmedia.dto.PostDTOs;
import com.leandre.socialmedia.model.Post;
import com.leandre.socialmedia.model.User;
import com.leandre.socialmedia.repository.PostRepository;
import com.leandre.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer for managing post-related operations, including creation, retrieval, and deletion.
 */
@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final String uploadDir = "./uploads";

    /**
     * Creates a new post with optional media upload.
     *
     * @param request The post request containing content and post type.
     * @param file The optional media file (image or video).
     * @param userId The ID of the user creating the post.
     * @return The response DTO for the created post.
     * @throws IOException If there is an error saving the media file.
     */
    public PostDTOs.PostResponse createPost(PostDTOs.PostRequest request, MultipartFile file, Long userId) throws IOException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String mediaUrl = null;
        if (file != null && !file.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get(uploadDir, fileName);
            try {
                Files.createDirectories(filePath.getParent());
                Files.write(filePath, file.getInputStream().readAllBytes());
            } catch (IOException e) {
                throw new IOException("Failed to save media file: " + e.getMessage(), e);
            }
            mediaUrl = filePath.toString();
        }

        Post post = Post.builder()
                .content(request.getContent())
                .mediaUrl(mediaUrl)
                .postType(Post.PostType.valueOf(request.getPostType().toUpperCase())) // Case-insensitive enum parsing
                .user(user)
                .build();
        post = postRepository.save(post);
        return mapToPostResponse(post);
    }

    /**
     * Retrieves a post by its ID.
     *
     * @param id The ID of the post to retrieve.
     * @return The response DTO for the retrieved post.
     */
    public PostDTOs.PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        return mapToPostResponse(post);
    }

    /**
     * Retrieves all posts.
     *
     * @return A list of post response DTOs.
     */
    public List<PostDTOs.PostResponse> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::mapToPostResponse)
                .collect(Collectors.toList());
    }

    /**
     * Deletes a post, only if the requesting user is the owner.
     *
     * @param id The ID of the post to delete.
     * @param userId The ID of the user requesting the deletion.
     */
    public void deletePost(Long id, Long userId) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));
        if (!post.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("Unauthorized");
        }
        postRepository.delete(post);
    }

    /**
     * Maps a Post entity to a PostResponse DTO.
     *
     * @param post The Post entity to map.
     * @return The mapped PostResponse DTO.
     */
    private PostDTOs.PostResponse mapToPostResponse(Post post) {
        PostDTOs.PostResponse response = new PostDTOs.PostResponse();
        response.setId(post.getId());
        response.setContent(post.getContent());
        response.setMediaUrl(post.getMediaUrl());
        response.setPostType(post.getPostType().name());
        response.setUserId(post.getUser().getId());
        response.setUsername(post.getUser().getUsername());
        return response;
    }
}