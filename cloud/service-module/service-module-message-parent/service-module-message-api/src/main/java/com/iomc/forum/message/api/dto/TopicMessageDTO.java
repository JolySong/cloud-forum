package com.iomc.forum.message.api.dto;

import lombok.Data;

@Data
public class TopicMessageDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 消息发送者用户id
     */
    private Long fromUserId;

    /**
     * 消息接收者用户id
     */
    private Long toUserId;


}
