package com.iomc.forum.topic.service;

import com.iomc.common.core.Res;

public interface LikeService {

    /**
     * 判断用户是否点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     * @return
     */
    boolean isLiked(Long userId, Long targetId, Integer targetType);

    /**
     * 点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     */
    void like(Long userId, Long targetId, Integer targetType);

    /**
     * 取消点赞
     *
     * @param userId
     * @param targetId
     * @param targetType
     */
    void unlike(Long userId, Long targetId, Integer targetType);

    /**
     * 获取用户的点赞列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    Res getMyLikes(Integer page, Integer size, Long userId, String keyword);
} 