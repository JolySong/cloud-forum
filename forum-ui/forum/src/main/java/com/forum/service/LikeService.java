package com.forum.service;

public interface LikeService {
    boolean isLiked(Long userId, Long targetId, String targetType);
    void like(Long userId, Long targetId, String targetType);
    void unlike(Long userId, Long targetId, String targetType);
} 