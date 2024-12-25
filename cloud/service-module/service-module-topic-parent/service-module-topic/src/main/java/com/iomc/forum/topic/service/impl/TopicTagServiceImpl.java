package com.iomc.forum.topic.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.topic.api.entity.TopicTag;
import com.iomc.forum.topic.api.vo.TagVO;
import com.iomc.forum.topic.dao.TopicTagDao;
import com.iomc.forum.topic.service.TopicTagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TopicTagServiceImpl extends ServiceImpl<TopicTagDao, TopicTag> implements TopicTagService {

    /**
     * 根据话题ID获取标签
     *
     * @param topicId
     * @return
     */
    @Override
    public List<TagVO> getTagsByTopicId(Long topicId) {
        return baseMapper.selectTagsByTopicId(topicId);
    }

    /**
     * 保存标签
     *
     * @param topicId
     * @param tags
     */
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
            baseMapper.insertBatchSomeColumn(topicTags);
        }
    }

    /**
     * 删除标签
     *
     * @param topicId
     */
    @Override
    public void deleteTags(Long topicId) {
        baseMapper.deleteByTopicId(topicId);
    }
} 