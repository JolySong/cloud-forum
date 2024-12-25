package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.topic.api.entity.Like;
import com.iomc.forum.topic.api.vo.UserLikeVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LikeDao extends SpiceBaseMapper<Like> {

    /**
     * 分页查询我的点赞列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     *
     * @return 点赞列表
     */
    IPage<UserLikeVO> selectLikeListByUserId(Page<UserLikeVO> page,
                                             @Param("userId") Long userId,
                                             @Param("keyword") String keyword);
} 