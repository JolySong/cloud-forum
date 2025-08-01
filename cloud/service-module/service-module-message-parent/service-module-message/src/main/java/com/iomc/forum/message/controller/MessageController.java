package com.iomc.forum.message.controller;

import com.iomc.common.core.Res;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.message.api.dto.MessageQueryDTO;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.api.vo.MessagePageVO;
import com.iomc.forum.message.service.IMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {

    private final IMessageService messageService;

    /**
     * 获取消息列表
     */
    @GetMapping
    public Res<MessagePageVO> getMessages(MessageQueryDTO queryDTO) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        MessagePageVO result = messageService.getMessagePage(queryDTO, currentUserId);
        return Res.success(result);
    }

    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread-count")
    public Res<Map<String, Long>> getUnreadCount() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        Map<String, Long> counts = messageService.getUnreadCounts(currentUserId);
        return Res.success(counts);
    }

    /**
     * 标记消息已读
     */
    @PutMapping("/{messageId}/read")
    public Res<Void> markAsRead(@PathVariable Long messageId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        boolean success = messageService.markAsRead(messageId, currentUserId);
        if (success) {
            return Res.success();
        } else {
            return Res.fail("消息不存在或无权限");
        }
    }

    /**
     * 批量标记消息已读
     */
    @PutMapping("/batch-read")
    public Res<Void> markBatchAsRead(@RequestBody List<Long> messageIds) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        int count = messageService.markBatchAsRead(messageIds, currentUserId);
        return Res.success();
    }

    /**
     * 全部标记已读
     */
    @PutMapping("/read-all")
    public Res<Void> markAllAsRead() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        int count = messageService.markAllAsRead(currentUserId);
        return Res.success();
    }

    /**
     * 使消息失效
     */
    @PutMapping("/{messageId}/invalidate")
    public Res<Void> invalidateMessage(@PathVariable Long messageId) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        boolean success = messageService.invalidateMessage(messageId, currentUserId);
        if (success) {
            return Res.success();
        } else {
            return Res.fail("消息不存在或无权限");
        }
    }

    /**
     * 创建消息
     */
    @PostMapping
    public Res<Void> createMessage(@RequestBody Message message) {
        boolean success = messageService.createMessage(message);
        if (success) {
            return Res.success();
        } else {
            return Res.fail("创建消息失败");
        }
    }

    /**
     * 批量创建消息
     */
    @PostMapping("/batch")
    public Res<Void> createBatchMessages(@RequestBody List<Message> messages) {
        boolean success = messageService.createBatchMessages(messages);
        if (success) {
            return Res.success();
        } else {
            return Res.fail("批量创建消息失败");
        }
    }
}
