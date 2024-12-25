package com.iomc.forum.topic.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.forum.topic.api.entity.TopicTag;
import com.iomc.forum.topic.api.vo.TagVO;

import java.util.List;

public interface TopicTagService extends IService<TopicTag> {

    /**
     * 根据话题id获取标签列表
     * @param topicId
     * @return
     */
    List<TagVO> getTagsByTopicId(Long topicId);

    /**
     * 保存主题选择的标签
     *
     * @param topicId
     * @param tags
     */
    void saveTags(Long topicId, List<Long> tags);

    /**
     * 删除标签
     *
     * @param topicId
     */
    void deleteTags(Long topicId);
} 