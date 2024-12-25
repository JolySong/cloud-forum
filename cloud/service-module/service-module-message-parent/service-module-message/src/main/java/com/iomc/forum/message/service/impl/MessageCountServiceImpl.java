package com.iomc.forum.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.message.api.entity.MessageCount;
import com.iomc.forum.message.dao.MessageCountDao;
import com.iomc.forum.message.service.IMessageCountService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息计数表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Service
public class MessageCountServiceImpl extends ServiceImpl<MessageCountDao, MessageCount> implements IMessageCountService {

}
