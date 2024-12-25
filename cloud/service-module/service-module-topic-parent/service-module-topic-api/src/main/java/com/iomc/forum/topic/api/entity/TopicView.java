package com.iomc.forum.topic.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 话题浏览记录
 */
@Data
@TableName("topic_views")
@AllArgsConstructor
public class TopicView {
    /**
     * 主键ID，自动生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /** 话题id */
    private Long topicId;
    /** 用户id */
    private Long userId;

    public TopicView(Long topicId, Long userId) {
        this.topicId = topicId;
        this.userId = userId;
    }
} 