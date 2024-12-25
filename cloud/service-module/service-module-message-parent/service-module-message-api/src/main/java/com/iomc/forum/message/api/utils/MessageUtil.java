package com.iomc.forum.message.api.utils;

import com.iomc.forum.message.api.dto.MessageDTO;

public class MessageUtil {

    /**
     * 系统消息id
     */
    public static final Long SYSTEM_USER_ID = 1000L;

    /**
     * 点赞消息类型
     */
    public static final Integer LIKE_TYPE = 0;
    /**
     * 评论消息类型
     */
    public static final Integer COMMENT_TYPE = 1;
    /**
     * 关注消息类型
     */
    public static final Integer FOLLOW_TYPE = 2;

    /**
     * 系统消息类型
     */
    public static final Integer SYSTEM_TYPE = 3;

    /**
     * 评论话题目标类型
     */
    public static final Integer TARGET_TOPIC_TYPE = 0;
    /**
     * 评论回复目标类型
     */
    public static final Integer TARGET_COMMENT_TYPE = 1;


    /**
     * 创建和修改主题发送待审核消息
     *
     * @param topicId
     * @param toUserId
     * @return
     */
    public static MessageDTO topicAuditMessage(Long topicId,
                                               Long toUserId) {

        MessageDTO message =
                createMessageDTO(SYSTEM_USER_ID, toUserId, topicId,
                        null, SYSTEM_TYPE, TARGET_TOPIC_TYPE);
        message.setIsRead(false);
        message.setFromUserId(SYSTEM_USER_ID);
        message.setToUserId(toUserId);
        message.setTargetType(COMMENT_TYPE);
        message.setTargetId(topicId);
        message.setType(TARGET_TOPIC_TYPE);
        message.setContent("你的话题已发起审核，请等待审核结果通知！");

        return message;
    }

    /**
     * 回复话题发送消息
     *
     * @param fromUserId 回复人
     * @param author     作者id
     * @param topicId    主题iid
     * @param commentId  评论id
     * @param content    回复内容
     * @return
     */
    public static MessageDTO replyToTopicMessage(Long fromUserId,
                                                 Long author,
                                                 Long topicId,
                                                 Long commentId,
                                                 String content) {

        MessageDTO message =
                createMessageDTO(fromUserId, author, topicId, commentId,
                        COMMENT_TYPE, TARGET_TOPIC_TYPE);
        message.setContent(content);

        return message;
    }

    /**
     * 回复评论发送消息
     *
     * @param fromUserId 回复人
     * @param toUserId   被回复人
     * @param topicId    主题iid
     * @param commentId  评论id
     * @param content    回复内容
     * @return
     */
    public static MessageDTO replyToCommentMessage(Long fromUserId,
                                                   Long toUserId,
                                                   Long topicId,
                                                   Long commentId,
                                                   String content) {
        MessageDTO message =
                createMessageDTO(fromUserId, toUserId, topicId, commentId,
                        COMMENT_TYPE, TARGET_COMMENT_TYPE);
        message.setContent(content);

        return message;
    }

    /**
     * 点赞主题消息
     *
     * @param fromUserId
     * @param toUserId
     * @param topicId
     * @return
     */
    public static MessageDTO likeTopicMessage(Long fromUserId,
                                              Long toUserId,
                                              Long topicId) {

        MessageDTO message =
                createMessageDTO(fromUserId, toUserId, topicId, null,
                        LIKE_TYPE, TARGET_TOPIC_TYPE);
        message.setContent("赞了我的话题！");

        return message;
    }

    /**
     * 点赞评论消息
     *
     * @param fromUserId
     * @param toUserId
     * @param topicId
     * @param commentId
     * @return
     */
    public static MessageDTO likeCommentMessage(Long fromUserId,
                                                Long toUserId,
                                                Long topicId,
                                                Long commentId) {
        MessageDTO message =
                createMessageDTO(fromUserId, toUserId, topicId, commentId,
                        LIKE_TYPE, TARGET_COMMENT_TYPE);

        message.setContent("赞了我的评论！");

        return message;
    }


    /**
     * 关注消息
     *
     * @param fromUserId
     * @param toUserId
     * @return
     */
    public static MessageDTO followMessage(Long fromUserId,
                                            Long toUserId) {

        MessageDTO message =
                createMessageDTO(fromUserId, toUserId, null, null, FOLLOW_TYPE, null);
        message.setContent("关注了你！");

        return message;
    }

    public static MessageDTO systemMessage(Long fromUserId,
                                            Long toUserId,
                                            String content) {

        MessageDTO message =
                createMessageDTO(fromUserId, toUserId, null, null,
                        SYSTEM_TYPE, TARGET_TOPIC_TYPE);
        message.setContent(content);

        return message;
    }


    /**
     * 创建消息对象
     *
     * @param fromUserId 发送人
     * @param toUserId   接收人
     * @param topicId    话题id
     * @param commentId  评论id
     * @param type       消息类型(0:点赞, 1:评论, 2:关注， 3系统)
     * @param targetType 目标类型(0:话题,1:评论)
     */
    private static MessageDTO createMessageDTO(Long fromUserId,
                                               Long toUserId,
                                               Long topicId,
                                               Long commentId,
                                               Integer type,
                                               Integer targetType) {
        MessageDTO message = new MessageDTO();
        message.setFromUserId(fromUserId);
        message.setToUserId(toUserId);
        message.setTopicId(topicId);
        message.setTargetId(commentId);
        message.setType(type);
        message.setTargetType(targetType);

        return message;
    }
}
