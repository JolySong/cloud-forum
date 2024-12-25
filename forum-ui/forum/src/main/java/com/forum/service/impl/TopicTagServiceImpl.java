package com.forum.service.impl;

import com.forum.entity.TopicTag;
import com.forum.mapper.TopicTagMapper;
import com.forum.service.TopicTagService;
import com.forum.vo.TagVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicTagServiceImpl implements TopicTagService {
    
    private final TopicTagMapper topicTagMapper;
    
    @Override
    public List<TagVO> getTagsByTopicId(Long topicId) {
        return topicTagMapper.selectTagsByTopicId(topicId);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveTags(Long topicId, List<Long> tags) {
        // 先删除原有标签
        deleteTags(topicId);
        // 保存新标签
        if (tags != null && !tags.isEmpty()) {
            List<TopicTag> topicTags = new java.util.ArrayList<>(tags.size());
            tags.forEach(tagId -> {
                TopicTag topicTag = new TopicTag();
                topicTag.setTopicId(topicId);
                topicTag.setTagId(tagId);
                topicTags.add(topicTag);
            });
            topicTagMapper.insertBatchSomeColumn(topicTags);
        }
    }
    
    @Override
    public void deleteTags(Long topicId) {
        topicTagMapper.deleteByTopicId(topicId);
    }
} 