package com.iomc.forum.message.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 关注事件
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class FollowEvent extends ApplicationEvent {
    
    private Long fromUserId;
    private String fromUserName;
    private Long toUserId;
    
    public FollowEvent(Object source, Long fromUserId, String fromUserName, Long toUserId) {
        super(source);
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
    }
} 