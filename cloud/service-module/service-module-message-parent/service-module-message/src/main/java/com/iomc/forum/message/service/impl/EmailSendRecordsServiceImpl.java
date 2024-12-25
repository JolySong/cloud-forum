package com.iomc.forum.message.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.constants.MessageConst;
import com.iomc.forum.message.api.dto.EmailSendDTO;
import com.iomc.forum.message.api.entity.EmailSendRecords;
import com.iomc.forum.message.api.mapstruct.EmailSendMapper;
import com.iomc.forum.message.api.vo.EmailSendVO;
import com.iomc.forum.message.dao.EmailSendRecordsDao;
import com.iomc.forum.message.service.IEmailSendRecordsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 邮件记录表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-11
 */
@Service
@Slf4j
public class EmailSendRecordsServiceImpl extends ServiceImpl<EmailSendRecordsDao, EmailSendRecords> implements IEmailSendRecordsService {

    // 这个是 mail 依赖提供给我们的发送邮件的接口
    @Autowired
    private JavaMailSender mailSender;

    // 获取发件人邮箱
    @Value("${spring.mail.username}")
    private String sender;

    // 获取发件人昵称
    @Value("${spring.mail.nickname:Tw}")
    private String nickname;

    /**
     * 发送安全校验邮件
     *
     * @param dto
     * @return
     */
    @Override
    public boolean sendSecurityEmail(EmailSendDTO dto) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(nickname + '<' + sender + '>');
        message.setTo(dto.getRecipientEmail());
        message.setSubject(dto.getSubject());
        message.setText(dto.getBody());

        boolean result = false;
        try {
            mailSender.send(message);
            result = true;
        } catch (Exception e) {
            log.error("发送邮件失败：" + e.getMessage());
        }

        // 修改状态
        dto.setStatus(result ? MessageConst.MESSAGE_STATUS_SUCCESS : MessageConst.MESSAGE_STATUS_FAILURE);
        dto.setEmailType(MessageConst.MESSAGE_TYPE_SECURITY);
        save(EmailSendMapper.INSTANCE.toEntity(dto));

        return result;
    }

    /**
     * 发送系统邮件
     *
     * @param dto
     */
    @Override
    public boolean sendSystemEmail(EmailSendDTO dto) {
        return false;
    }

    /**
     * 获取邮件发送记录列表
     *
     * @param page
     * @param dto
     */
    @Override
    public Page getEmailSendRecordsList(Page<EmailSendRecords> page, EmailSendDTO dto) {


        Page<EmailSendRecords> emailSendRecordsPage =
                baseMapper.selectPage(page,
                        new LambdaQueryWrapper<EmailSendRecords>()
                                .orderByDesc(EmailSendRecords::getCreatedAt));

        if (emailSendRecordsPage != null && emailSendRecordsPage.getSize() > 0L) {
            List<EmailSendRecords> records = emailSendRecordsPage.getRecords();

            List<EmailSendVO> listVO = new ArrayList<>();
            records.forEach(record -> listVO.add(EmailSendMapper.INSTANCE.toVO(record)));

            return new Page<EmailSendVO> (
                    emailSendRecordsPage.getCurrent(),
                    emailSendRecordsPage.getSize(),
                    emailSendRecordsPage.getTotal())
                    .setRecords(listVO);
        }

        return emailSendRecordsPage;
    }
}
