package com.iomc.forum.message.listener;

import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.event.CommentEvent;
import com.iomc.forum.message.event.FollowEvent;
import com.iomc.forum.message.event.LikeEvent;
import com.iomc.forum.message.event.SystemNotificationEvent;
import com.iomc.forum.message.service.IMessageService;
import com.iomc.forum.message.utils.MessageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 消息事件监听器
 *
 * @author forum
 * @since 2024-12-25
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class MessageEventListener {

    private final IMessageService messageService;

    /**
     * 处理点赞事件
     */
    @Async
    @EventListener
    public void handleLikeEvent(LikeEvent event) {
        try {
            // 避免自己给自己点赞时发送消息
            if (event.getFromUserId().equals(event.getToUserId())) {
                return;
            }
            
            String content = String.format("用户 %s 点赞了你的%s", 
                event.getFromUserName(), 
                event.getTargetType() == 0 ? "话题" : "评论");
            
            Message message = MessageUtils.createLikeMessage(
                event.getFromUserId(),
                event.getToUserId(),
                event.getTargetType(),
                event.getTargetId(),
                event.getTopicId(),
                content
            );
            
            messageService.createMessage(message);
            log.info("创建点赞消息成功: {}", message.getId());
        } catch (Exception e) {
            log.error("创建点赞消息失败", e);
        }
    }

    /**
     * 处理评论事件
     */
    @Async
    @EventListener
    public void handleCommentEvent(CommentEvent event) {
        try {
            // 避免自己给自己评论时发送消息
            if (event.getFromUserId().equals(event.getToUserId())) {
                return;
            }
            
            String content = String.format("用户 %s 评论了你的%s", 
                event.getFromUserName(), 
                event.getTargetType() == 0 ? "话题" : "评论");
            
            Message message = MessageUtils.createCommentMessage(
                event.getFromUserId(),
                event.getToUserId(),
                event.getTargetType(),
                event.getTargetId(),
                event.getTopicId(),
                content
            );
            
            messageService.createMessage(message);
            log.info("创建评论消息成功: {}", message.getId());
        } catch (Exception e) {
            log.error("创建评论消息失败", e);
        }
    }

    /**
     * 处理关注事件
     */
    @Async
    @EventListener
    public void handleFollowEvent(FollowEvent event) {
        try {
            String content = String.format("用户 %s 关注了你", event.getFromUserName());
            
            Message message = MessageUtils.createFollowMessage(
                event.getFromUserId(),
                event.getToUserId(),
                content
            );
            
            messageService.createMessage(message);
            log.info("创建关注消息成功: {}", message.getId());
        } catch (Exception e) {
            log.error("创建关注消息失败", e);
        }
    }

    /**
     * 处理系统通知事件
     */
    @Async
    @EventListener
    public void handleSystemNotificationEvent(SystemNotificationEvent event) {
        try {
            Message message = MessageUtils.createSystemMessage(
                event.getToUserId(),
                event.getContent()
            );
            
            messageService.createMessage(message);
            log.info("创建系统通知成功: {}", message.getId());
        } catch (Exception e) {
            log.error("创建系统通知失败", e);
        }
    }
} 