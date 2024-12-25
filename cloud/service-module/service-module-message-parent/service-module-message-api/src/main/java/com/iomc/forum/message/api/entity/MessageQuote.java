package com.iomc.forum.message.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 消息引用内容表
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("message_quote")
public class MessageQuote implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 引用ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 消息ID
     */
    private Long messageId;

    /**
     * 引用标题
     */
    private String title;

    /**
     * 引用内容
     */
    private String content;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;


}
