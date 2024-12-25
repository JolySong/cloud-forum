package com.forum.service;

public interface FollowService {
    boolean isFollowed(Long userId, Long followedId);
    void follow(Long userId, Long followedId);
    void unfollow(Long userId, Long followedId);
} 