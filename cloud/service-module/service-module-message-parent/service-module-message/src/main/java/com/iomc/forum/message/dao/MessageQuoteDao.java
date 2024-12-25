package com.iomc.forum.message.dao;

import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.message.api.entity.MessageQuote;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息引用内容表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Mapper
public interface MessageQuoteDao extends SpiceBaseMapper<MessageQuote> {

}
