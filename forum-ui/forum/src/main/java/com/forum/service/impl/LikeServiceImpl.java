package com.forum.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.forum.entity.Like;
import com.forum.mapper.LikeMapper;
import com.forum.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    private final LikeMapper likeMapper;

    @Override
    public boolean isLiked(Long userId, Long targetId, String targetType) {
        return likeMapper.countByUserAndTarget(userId, targetId, targetType) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Long userId, Long targetId, String targetType) {
        if (!isLiked(userId, targetId, targetType)) {
            Like like = new Like();
            like.setUserId(userId);
            like.setTargetId(targetId);
            like.setTargetType(targetType);
            likeMapper.insert(like);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Long userId, Long targetId, String targetType) {
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId)
                .eq(Like::getTargetId, targetId)
                .eq(Like::getTargetType, targetType);
        likeMapper.delete(wrapper);
    }
} 