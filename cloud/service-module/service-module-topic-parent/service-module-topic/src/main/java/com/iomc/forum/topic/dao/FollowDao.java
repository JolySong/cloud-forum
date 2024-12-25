package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iomc.forum.topic.api.entity.Follow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FollowDao extends BaseMapper<Follow> {
    
    @Select("SELECT COUNT(*) FROM follows WHERE user_id = #{userId} AND followed_id = #{followedId}")
    int countByUserAndFollowed(@Param("userId") Long userId, @Param("followedId") Long followedId);
} 