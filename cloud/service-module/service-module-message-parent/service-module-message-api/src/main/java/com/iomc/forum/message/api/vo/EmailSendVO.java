package com.iomc.forum.message.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmailSendVO {

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
     * 发送失败时的错误信息（可选）
     */
    private String errorMessage;
}
