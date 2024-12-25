package com.iomc.forum.message.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.forum.message.api.dto.EmailSendDTO;
import com.iomc.forum.message.api.entity.EmailSendRecords;

/**
 * <p>
 * 邮件记录表 服务类
 * </p>
 *
 * @author song
 * @since 2024-12-11
 */
public interface IEmailSendRecordsService extends IService<EmailSendRecords> {

    /**
     * 发送安全校验邮件
     *
     * @param dto
     * @return
     */
    boolean sendSecurityEmail(EmailSendDTO dto);

    /**
     * 发送系统邮件
     *
     * @param dto
     *
     */
    boolean sendSystemEmail(EmailSendDTO dto);

    /**
     * 获取邮件发送记录列表
     *
     * @param page
     * @param dto
     */
    Page getEmailSendRecordsList(Page<EmailSendRecords> page, EmailSendDTO dto);
}
