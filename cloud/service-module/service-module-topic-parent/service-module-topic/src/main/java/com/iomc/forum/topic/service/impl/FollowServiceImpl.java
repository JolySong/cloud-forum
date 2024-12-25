package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.topic.api.entity.Follow;
import com.iomc.forum.topic.dao.FollowDao;
import com.iomc.forum.topic.service.FollowService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FollowServiceImpl extends ServiceImpl<FollowDao, Follow> implements FollowService {


    @Override
    public boolean isFollowed(Long userId, Long followedId) {
        return baseMapper.countByUserAndFollowed(userId, followedId) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void follow(Long userId, Long followedId) {
        if (!isFollowed(userId, followedId)) {
            Follow follow = new Follow();
            follow.setUserId(userId);
            follow.setFollowedId(followedId);
            baseMapper.insert(follow);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unfollow(Long userId, Long followedId) {
        LambdaQueryWrapper<Follow> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Follow::getUserId, userId)
                .eq(Follow::getFollowedId, followedId);
        baseMapper.delete(wrapper);
    }
} 