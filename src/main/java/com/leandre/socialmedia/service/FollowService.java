package com.leandre.socialmedia.service;

import com.leandre.socialmedia.dto.FollowDTO;
import com.leandre.socialmedia.dto.UserDTOs;
import com.leandre.socialmedia.model.Follow;
import com.leandre.socialmedia.model.User;
import com.leandre.socialmedia.repository.FollowRepository;
import com.leandre.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowDTO.FollowResponse followUser(FollowDTO.FollowRequest request, Long followerId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new IllegalArgumentException("Follower not found"));
        User followed = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (followerId.equals(request.getUserId())) {
            throw new IllegalArgumentException("Cannot follow yourself");
        }

        if (followRepository.findByFollowerIdAndFollowedId(followerId, request.getUserId()).isPresent()) {
            throw new IllegalArgumentException("Already following this user");
        }

        Follow follow = Follow.builder()
                .follower(follower)
                .followed(followed)
                .build();
        follow = followRepository.save(follow);
        return mapToFollowResponse(follow);
    }

    public void unfollowUser(Long followedId, Long followerId) {
        Follow follow = followRepository.findByFollowerIdAndFollowedId(followerId, followedId)
                .orElseThrow(() -> new IllegalArgumentException("Follow relationship not found"));
        followRepository.delete(follow);
    }

    public List<UserDTOs.UserResponse> getFollowing(Long userId) {
        return followRepository.findByFollowerId(userId).stream()
                .map(follow -> mapToUserResponse(follow.getFollowed()))
                .collect(Collectors.toList());
    }

    public List<UserDTOs.UserResponse> getFollowers(Long userId) {
        return followRepository.findByFollowedId(userId).stream()
                .map(follow -> mapToUserResponse(follow.getFollower()))
                .collect(Collectors.toList());
    }

    private FollowDTO.FollowResponse mapToFollowResponse(Follow follow) {
        FollowDTO.FollowResponse response = new FollowDTO.FollowResponse();
        response.setId(follow.getId());
        response.setFollowerId(follow.getFollower().getId());
        response.setFollowedId(follow.getFollowed().getId());
        return response;
    }

    private UserDTOs.UserResponse mapToUserResponse(User user) {
        UserDTOs.UserResponse response = new UserDTOs.UserResponse();
        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        return response;
    }
}