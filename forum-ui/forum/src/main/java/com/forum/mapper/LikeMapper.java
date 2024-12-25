package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Like;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface LikeMapper extends BaseMapper<Like> {
    
    @Select("SELECT COUNT(*) FROM likes WHERE user_id = #{userId} " +
            "AND target_id = #{targetId} AND target_type = #{targetType}")
    int countByUserAndTarget(@Param("userId") Long userId,
                           @Param("targetId") Long targetId,
                           @Param("targetType") String targetType);
} 