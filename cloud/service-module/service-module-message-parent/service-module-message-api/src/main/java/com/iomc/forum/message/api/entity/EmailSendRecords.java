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
 * 邮件记录表
 * </p>
 *
 * @author song
 * @since 2024-12-11
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("email_send_records")
public class EmailSendRecords implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 唯一标识符
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 收件人邮箱
     */
    private String recipientEmail;

    /**
     * 邮件主题
     */
    private String subject;

    /**
     * 邮件内容
     */
    private String body;

    /**
     * 邮件类型（0安全验证 1系统通知）
     */
    private Integer emailType;

    /**
     * 发送状态（如：0失败 1成功）
     */
    private Integer status;

    /**
     * 记录创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 记录更新时间
     */
    private LocalDateTime updatedAt;

    /**
     * 发送失败时的错误信息（可选）
     */
    private String errorMessage;
}
