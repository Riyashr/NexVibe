package com.leandre.socialmedia.service;

import com.leandre.socialmedia.dto.CommentDTO;
import com.leandre.socialmedia.model.Comment;
import com.leandre.socialmedia.model.Post;
import com.leandre.socialmedia.model.User;
import com.leandre.socialmedia.repository.CommentRepository;
import com.leandre.socialmedia.repository.PostRepository;
import com.leandre.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentDTO.CommentResponse addComment(Long postId, CommentDTO.CommentRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        Comment comment = Comment.builder()
                .content(request.getContent())
                .user(user)
                .post(post)
                .build();
        comment = commentRepository.save(comment);
        return mapToCommentResponse(comment);
    }

    public List<CommentDTO.CommentResponse> getCommentsForPost(Long postId) {
        return commentRepository.findByPostId(postId).stream()
                .map(this::mapToCommentResponse)
                .collect(Collectors.toList());
    }

    private CommentDTO.CommentResponse mapToCommentResponse(Comment comment) {
        CommentDTO.CommentResponse response = new CommentDTO.CommentResponse();
        response.setId(comment.getId());
        response.setContent(comment.getContent());
        response.setUserId(comment.getUser().getId());
        response.setUsername(comment.getUser().getUsername());
        response.setPostId(comment.getPost().getId());
        return response;
    }
}