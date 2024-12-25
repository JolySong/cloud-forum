package com.iomc.forum.topic.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.entity.Like;
import com.iomc.forum.topic.dao.LikeDao;
import com.iomc.forum.topic.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {

    @Autowired
    private LikeDao likeMapper;

    /**
     * 判断用户是否点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     * @return
     */
    @Override
    public boolean isLiked(Long userId, Long targetId, Integer targetType) {
        Long l = likeMapper.selectCount(
                new LambdaQueryWrapper<Like>()
                        .eq(Like::getUserId, userId)
                        .eq(Like::getTargetId, targetId)
                        .eq(Like::getTargetType, targetType)
        );


        return l != null && l > 0;
    }

    /**
     * 点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void like(Long userId, Long targetId, Integer targetType) {
        if (!isLiked(userId, targetId, targetType)) {
            Like like = new Like();
            like.setUserId(userId);
            like.setTargetId(targetId);
            like.setTargetType(targetType);
            likeMapper.insert(like);
        }
    }

    /**
     * 取消点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlike(Long userId, Long targetId, Integer targetType) {
        LambdaQueryWrapper<Like> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Like::getUserId, userId)
                .eq(Like::getTargetId, targetId)
                .eq(Like::getTargetType, targetType);
        likeMapper.delete(wrapper);
    }

    /**
     * 获取用户的点赞列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @Override
    public Res getMyLikes(Integer page, Integer size, Long userId, String keyword) {
        return Res.success(likeMapper.selectLikeListByUserId(
                Page.of(page, size), userId, keyword));
    }
}
