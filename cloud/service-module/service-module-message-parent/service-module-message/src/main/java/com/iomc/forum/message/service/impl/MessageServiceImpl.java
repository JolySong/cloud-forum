package com.iomc.forum.message.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.message.api.dto.MessageQueryDTO;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.api.vo.MessagePageVO;
import com.iomc.forum.message.api.vo.MessageVO;
import com.iomc.forum.message.dao.MessageDao;
import com.iomc.forum.message.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Service
@RequiredArgsConstructor
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements IMessageService {

    private final MessageDao messageDao;

    @Override
    public MessagePageVO getMessagePage(MessageQueryDTO queryDTO, Long userId) {
        // 创建分页对象
        Page<MessageVO> page = new Page<>(queryDTO.getPage(), queryDTO.getSize());
        
        // 查询消息列表
        IPage<MessageVO> messagePage = messageDao.selectMessagePage(
            page, 
            userId, 
            queryDTO.getType(), 
            queryDTO.getIsRead()
        );
        
        // 获取未读消息统计
        Map<String, Long> unreadCounts = getUnreadCounts(userId);
        
        // 构建返回结果
        MessagePageVO result = new MessagePageVO();
        result.setRecords(messagePage.getRecords());
        result.setTotal(messagePage.getTotal());
        result.setCurrent(messagePage.getCurrent());
        result.setSize(messagePage.getSize());
        result.setUnreadCounts(unreadCounts);
        
        return result;
    }

    @Override
    public Map<String, Long> getUnreadCounts(Long userId) {
        Map<String, Long> counts = messageDao.selectUnreadCounts(userId);

        final Long defaultCount = 0L;
        
        // 确保所有类型都有值，避免前端显示null
        Map<String, Long> result = new HashMap<>();
        result.put("all", counts.getOrDefault("all_count", defaultCount));
        result.put("like", counts.getOrDefault("like_count", defaultCount));
        result.put("comment", counts.getOrDefault("comment_count", defaultCount));
        result.put("system", counts.getOrDefault("system_count", defaultCount));
        
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean markAsRead(Long messageId, Long userId) {
        // 验证消息是否存在且属于当前用户
        Message message = this.lambdaQuery()
            .eq(Message::getId, messageId)
            .eq(Message::getToUserId, userId)
            .eq(Message::getStatus, true)
            .one();
            
        if (message == null) {
            return false;
        }
        
        // 更新已读状态
        message.setIsRead(true);
        message.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int markBatchAsRead(List<Long> messageIds, Long userId) {
        if (messageIds == null || messageIds.isEmpty()) {
            return 0;
        }
        
        return messageDao.updateBatchRead(userId, messageIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int markAllAsRead(Long userId) {
        return messageDao.updateAllRead(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMessage(Message message) {
        if (message == null) {
            return false;
        }
        
        // 设置默认值
        if (message.getIsRead() == null) {
            message.setIsRead(false);
        }
        if (message.getStatus() == null) {
            message.setStatus(true);
        }
        if (message.getCreatedAt() == null) {
            message.setCreatedAt(LocalDateTime.now());
        }
        if (message.getUpdatedAt() == null) {
            message.setUpdatedAt(LocalDateTime.now());
        }
        
        return this.save(message);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createBatchMessages(List<Message> messages) {
        if (messages == null || messages.isEmpty()) {
            return false;
        }
        
        // 设置默认值
        LocalDateTime now = LocalDateTime.now();
        for (Message message : messages) {
            if (message.getIsRead() == null) {
                message.setIsRead(false);
            }
            if (message.getStatus() == null) {
                message.setStatus(true);
            }
            if (message.getCreatedAt() == null) {
                message.setCreatedAt(now);
            }
            if (message.getUpdatedAt() == null) {
                message.setUpdatedAt(now);
            }
        }
        
        return this.saveBatch(messages);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean invalidateMessage(Long messageId, Long userId) {
        // 验证消息是否存在且属于当前用户
        Message message = this.lambdaQuery()
            .eq(Message::getId, messageId)
            .eq(Message::getToUserId, userId)
            .one();
            
        if (message == null) {
            return false;
        }
        
        // 设置消息失效
        message.setStatus(false);
        message.setUpdatedAt(LocalDateTime.now());
        
        return this.updateById(message);
    }
}
