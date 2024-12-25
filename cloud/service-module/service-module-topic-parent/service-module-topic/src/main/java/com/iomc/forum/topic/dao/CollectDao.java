package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iomc.forum.topic.api.entity.Collect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CollectDao extends BaseMapper<Collect> {
    
    @Select("SELECT COUNT(*) FROM collects WHERE user_id = #{userId} AND topic_id = #{topicId}")
    int countByUserAndTopic(@Param("userId") Long userId, @Param("topicId") Long topicId);
} 