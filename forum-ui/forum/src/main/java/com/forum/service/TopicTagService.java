package com.forum.service;

import com.forum.vo.TagVO;

import java.util.List;

public interface TopicTagService {
    List<TagVO> getTagsByTopicId(Long topicId);
    void saveTags(Long topicId, List<Long> tags);
    void deleteTags(Long topicId);
} 