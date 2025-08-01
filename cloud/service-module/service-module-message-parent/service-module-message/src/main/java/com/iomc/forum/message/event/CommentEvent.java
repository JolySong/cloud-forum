package com.iomc.forum.message.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 评论事件
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class CommentEvent extends ApplicationEvent {
    
    private Long fromUserId;
    private String fromUserName;
    private Long toUserId;
    private Integer targetType; // 0:话题, 1:评论
    private Long targetId;
    private Long topicId;
    
    public CommentEvent(Object source, Long fromUserId, String fromUserName, 
                       Long toUserId, Integer targetType, Long targetId, Long topicId) {
        super(source);
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
        this.targetType = targetType;
        this.targetId = targetId;
        this.topicId = topicId;
    }
} 