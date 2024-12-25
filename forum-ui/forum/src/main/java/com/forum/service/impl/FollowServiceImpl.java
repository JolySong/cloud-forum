package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.forum.entity.Follow;
import com.forum.mapper.FollowMapper;
import com.forum.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl implements FollowService {

    private final FollowMapper followMapper;

    @Override
    public boolean isFollowed(Long userId, Long followedId) {
        return followMapper.countByUserAndFollowed(userId, followedId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void follow(Long userId, Long followedId) {
        if (!isFollowed(userId, followedId)) {
            Follow follow = new Follow();
            follow.setUserId(userId);
            follow.setFollowedId(followedId);
            followMapper.insert(follow);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfollow(Long userId, Long followedId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId)
                .eq(Follow::getFollowedId, followedId);
        followMapper.delete(wrapper);
    }
} 