package com.iomc.forum.message.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.message.api.entity.Message;
import com.iomc.forum.message.dao.MessageDao;
import com.iomc.forum.message.service.IMessageService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageDao, Message> implements IMessageService {

}
