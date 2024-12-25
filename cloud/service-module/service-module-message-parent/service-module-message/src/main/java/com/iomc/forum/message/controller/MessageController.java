package com.iomc.forum.message.controller;


import com.iomc.common.core.Res;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.service.IMessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 消息表 前端控制器
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Controller
@RequestMapping("/message")
public class MessageController {


    private final IMessageService messageService;

    public MessageController(IMessageService messageService) {
        this.messageService = messageService;
    }

    /**
     * 未读消息数量
     *
     */
    @GetMapping("/unread-count")
    public Res unreadCount() {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        return Res.success(messageService.lambdaQuery()
                .eq(Message::getToUserId, currentUserId)
                .eq(Message::getIsRead, false)
                .eq(Message::getStatus, true)
                .count());
    }

}
