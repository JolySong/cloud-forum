package com.forum.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("topic_views")
public class TopicView {
    private Long topicId;
    private Long userId;
} 