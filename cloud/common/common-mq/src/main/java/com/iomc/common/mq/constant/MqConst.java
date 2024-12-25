package com.iomc.common.mq.constant;

public interface MqConst {

    String TOPIC_EMAIL_SEND = "email_send";

    String TOPIC_EMAIL_SEND_RECORDS = "email_send_records";

    /**
     * 消息服务消息队列
     */
    String SERVICE_MODULE_MESSAGE_TOPIC = "service-module-message-topic";

    /**
     * 认证服务消息队列
     */
    String SERVICE_AUTH_MESSAGE_TOPIC = "service-auth-message-topic";

    /**
     * 认证服务消息组
     */
    String SERVICE_AUTH_MESSAGE_GROUP = "service-auth-message-group";

    /**
     * 分析服务消息队列
     */
    String SERVICE_ANALYSIS_MESSAGE_TOPIC = "service-analysis-message-topic";

    /**
     * 论坛服务消息队列
     */
    String SERVICE_FORUM_MESSAGE_TOPIC = "service-forum-message-topic";

    /** 主题消息标签 */
    String TAG_TOPIC_MESSAGE = "topic_tag";
    /** 点赞消息标签 */
    String TAG_LIKE_MESSAGE = "like_tag";
    /** 关注消息标签 */
    String TAG_FOLLOW_MESSAGE = "follow_tag";
    /** 评论消息标签 */
    String TAG_COMMENT_MESSAGE = "comment_tag";

    /**
     * 消息ID KEY
     */
    String MESSAGE_ID_KEY = "mq:message:id:";
}
