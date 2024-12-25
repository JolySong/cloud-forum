package com.forum.mapper;

import com.forum.config.SpiceBaseMapper;
import com.forum.entity.TopicTag;
import com.forum.vo.TagVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface TopicTagMapper extends SpiceBaseMapper<TopicTag> {

    @Select("SELECT t.name, t.color FROM tags t " +
            "INNER JOIN topic_tags tt ON t.id = tt.tag_id " +
            "WHERE tt.topic_id = #{topicId}")
    List<TagVO> selectTagsByTopicId(@Param("topicId") Long topicId);
    
    @Delete("DELETE FROM topic_tags WHERE topic_id = #{topicId}")
    int deleteByTopicId(@Param("topicId") Long topicId);
} 