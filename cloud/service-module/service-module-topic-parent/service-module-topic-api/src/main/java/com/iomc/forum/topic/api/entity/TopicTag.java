package com.iomc.forum.topic.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 话题标签关联表
 *
 */
@Data
@TableName("topic_tags")
public class TopicTag {
    /**
     * 主键ID，自动生成
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 主题ID
     */
    private Long topicId;

    /**
     * 标签ID
     */
    private Long tagId;
}
