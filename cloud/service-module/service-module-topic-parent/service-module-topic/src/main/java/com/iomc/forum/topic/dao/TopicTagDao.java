package com.iomc.forum.topic.dao;


import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.topic.api.entity.TopicTag;
import com.iomc.forum.topic.api.vo.TagVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TopicTagDao extends SpiceBaseMapper<TopicTag> {

    /**
     * 根据话题id查询话题标签
     *
     * @param topicId
     * @return
     */
    @Select("SELECT t.id, t.name, t.color FROM tags t " +
            "INNER JOIN topic_tags tt ON t.id = tt.tag_id " +
            "WHERE tt.topic_id = #{topicId}")
    List<TagVO> selectTagsByTopicId(@Param("topicId") Long topicId);

    /**
     * 根据话题id删除话题标签
     *
     * @param topicId
     * @return
     */
    @Delete("DELETE FROM topic_tags WHERE topic_id = #{topicId}")
    int deleteByTopicId(@Param("topicId") Long topicId);
} 