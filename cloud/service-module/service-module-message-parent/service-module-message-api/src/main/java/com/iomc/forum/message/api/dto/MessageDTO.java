package com.iomc.forum.message.api.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 消息ID
     */
    private Long id;

    /**
     * 消息类型(0:点赞, 1:评论, 2:关注， 3系统)
     */
    private Integer type;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 发送者用户ID
     */
    private Long fromUserId;

    /**
     * 接收者用户ID
     */
    private Long toUserId;

    /**
     * 目标类型(0:话题,1:评论)
     */
    private Integer targetType;

    /**
     * 目标ID
     */
    private Long targetId;

    /**
     * 话题ID(评论类消息)
     */
    private Long topicId;

    /**
     * 是否已读(0:未读,1:已读)
     */
    private Boolean isRead;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 消息状态(0:已失效,1:有效)
     */
    private Boolean status;
}
