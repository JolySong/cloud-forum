package com.forum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("topic_tags")
public class TopicTag {
    private Long topicId;
    private Long tagId;
} 