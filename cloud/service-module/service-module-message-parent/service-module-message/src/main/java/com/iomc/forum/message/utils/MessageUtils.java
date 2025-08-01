package com.iomc.forum.message.utils;

import com.iomc.forum.message.api.entity.Message;

import java.time.LocalDateTime;

/**
 * 消息工具类
 *
 * @author forum
 * @since 2024-12-25
 */
public class MessageUtils {

    /**
     * 创建点赞消息
     *
     * @param fromUserId 发送者用户ID
     * @param toUserId 接收者用户ID
     * @param targetType 目标类型 (0:话题, 1:评论)
     * @param targetId 目标ID
     * @param topicId 话题ID
     * @param content 消息内容
     * @return 消息实体
     */
    public static Message createLikeMessage(Long fromUserId, Long toUserId, 
                                         Integer targetType, Long targetId, 
                                         Long topicId, String content) {
        return createMessage(fromUserId, toUserId, 0, targetType, targetId, topicId, content);
    }

    /**
     * 创建评论消息
     *
     * @param fromUserId 发送者用户ID
     * @param toUserId 接收者用户ID
     * @param targetType 目标类型 (0:话题, 1:评论)
     * @param targetId 目标ID
     * @param topicId 话题ID
     * @param content 消息内容
     * @return 消息实体
     */
    public static Message createCommentMessage(Long fromUserId, Long toUserId, 
                                            Integer targetType, Long targetId, 
                                            Long topicId, String content) {
        return createMessage(fromUserId, toUserId, 1, targetType, targetId, topicId, content);
    }

    /**
     * 创建关注消息
     *
     * @param fromUserId 发送者用户ID
     * @param toUserId 接收者用户ID
     * @param content 消息内容
     * @return 消息实体
     */
    public static Message createFollowMessage(Long fromUserId, Long toUserId, String content) {
        return createMessage(fromUserId, toUserId, 2, null, null, null, content);
    }

    /**
     * 创建系统消息
     *
     * @param toUserId 接收者用户ID
     * @param content 消息内容
     * @return 消息实体
     */
    public static Message createSystemMessage(Long toUserId, String content) {
        return createMessage(null, toUserId, 3, null, null, null, content);
    }

    /**
     * 创建消息
     *
     * @param fromUserId 发送者用户ID
     * @param toUserId 接收者用户ID
     * @param type 消息类型 (0:点赞, 1:评论, 2:关注, 3:系统)
     * @param targetType 目标类型 (0:话题, 1:评论)
     * @param targetId 目标ID
     * @param topicId 话题ID
     * @param content 消息内容
     * @return 消息实体
     */
    public static Message createMessage(Long fromUserId, Long toUserId, Integer type,
                                     Integer targetType, Long targetId, Long topicId, String content) {
        Message message = new Message();
        message.setType(type);
        message.setContent(content);
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setTargetType(targetType);
        message.setTargetId(targetId);
        message.setTopicId(topicId);
        message.setIsRead(false);
        message.setStatus(true);
        message.setCreatedAt(LocalDateTime.now());
        message.setUpdatedAt(LocalDateTime.now());
        
        return message;
    }

    /**
     * 获取消息类型描述
     *
     * @param type 消息类型
     * @return 类型描述
     */
    public static String getMessageTypeDesc(Integer type) {
        if (type == null) {
            return "未知";
        }
        
        switch (type) {
            case 0:
                return "点赞";
            case 1:
                return "评论";
            case 2:
                return "关注";
            case 3:
                return "系统通知";
            default:
                return "未知";
        }
    }

    /**
     * 获取目标类型描述
     *
     * @param targetType 目标类型
     * @return 类型描述
     */
    public static String getTargetTypeDesc(Integer targetType) {
        if (targetType == null) {
            return "未知";
        }
        
        switch (targetType) {
            case 0:
                return "话题";
            case 1:
                return "评论";
            default:
                return "未知";
        }
    }
} 