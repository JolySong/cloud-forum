package com.iomc.forum.topic.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.forum.topic.api.entity.Follow;

public interface FollowService extends IService<Follow> {
    boolean isFollowed(Long userId, Long followedId);
    void follow(Long userId, Long followedId);
    void unfollow(Long userId, Long followedId);
} 