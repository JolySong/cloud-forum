package com.iomc.forum.message.dao;

import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.message.api.entity.Message;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 消息表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Mapper
public interface MessageDao extends SpiceBaseMapper<Message> {

}
