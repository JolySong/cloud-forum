package com.iomc.forum.message.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.forum.message.api.entity.MessageQuote;
import com.iomc.forum.message.dao.MessageQuoteDao;
import com.iomc.forum.message.service.IMessageQuoteService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息引用内容表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Service
public class MessageQuoteServiceImpl extends ServiceImpl<MessageQuoteDao, MessageQuote> implements IMessageQuoteService {

}
