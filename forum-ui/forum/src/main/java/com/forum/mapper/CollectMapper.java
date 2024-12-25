package com.forum.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.forum.entity.Collect;
import com.forum.entity.Tag;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CollectMapper extends BaseMapper<Collect> {
    
    @Select("SELECT COUNT(*) FROM collects WHERE user_id = #{userId} AND topic_id = #{topicId}")
    int countByUserAndTopic(@Param("userId") Long userId, @Param("topicId") Long topicId);
} 