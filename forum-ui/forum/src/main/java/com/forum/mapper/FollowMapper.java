package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Follow;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface FollowMapper extends BaseMapper<Follow> {
    
    @Select("SELECT COUNT(*) FROM follows WHERE user_id = #{userId} AND followed_id = #{followedId}")
    int countByUserAndFollowed(@Param("userId") Long userId, @Param("followedId") Long followedId);
} 