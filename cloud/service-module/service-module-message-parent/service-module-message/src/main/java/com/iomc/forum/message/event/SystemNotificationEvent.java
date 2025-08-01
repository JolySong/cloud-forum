package com.iomc.forum.message.event;

import lombok.Data;
import org.springframework.context.ApplicationEvent;

/**
 * 系统通知事件
 *
 * @author forum
 * @since 2024-12-25
 */
@Data
public class SystemNotificationEvent extends ApplicationEvent {
    
    private Long toUserId;
    private String content;
    
    public SystemNotificationEvent(Object source, Long toUserId, String content) {
        super(source);
        this.toUserId = toUserId;
        this.content = content;
    }
} 