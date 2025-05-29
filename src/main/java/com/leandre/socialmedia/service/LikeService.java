package com.leandre.socialmedia.service;

import com.leandre.socialmedia.dto.LikeDTO;
import com.leandre.socialmedia.model.Like;
import com.leandre.socialmedia.model.Post;
import com.leandre.socialmedia.model.User;
import com.leandre.socialmedia.repository.LikeRepository;
import com.leandre.socialmedia.repository.PostRepository;
import com.leandre.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {
    private final LikeRepository likeRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public LikeDTO.LikeResponse addLike(LikeDTO.LikeRequest request, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("Post not found"));

        if (likeRepository.findByUserIdAndPostId(userId, request.getPostId()).isPresent()) {
            throw new IllegalArgumentException("Post already liked");
        }

        Like like = Like.builder()
                .user(user)
                .post(post)
                .build();
        like = likeRepository.save(like);
        return mapToLikeResponse(like);
    }

    public void removeLike(Long postId, Long userId) {
        Like like = likeRepository.findByUserIdAndPostId(userId, postId)
                .orElseThrow(() -> new IllegalArgumentException("Like not found"));
        likeRepository.delete(like);
    }

    private LikeDTO.LikeResponse mapToLikeResponse(Like like) {
        LikeDTO.LikeResponse response = new LikeDTO.LikeResponse();
        response.setId(like.getId());
        response.setUserId(like.getUser().getId());
        response.setPostId(like.getPost().getId());
        return response;
    }
}