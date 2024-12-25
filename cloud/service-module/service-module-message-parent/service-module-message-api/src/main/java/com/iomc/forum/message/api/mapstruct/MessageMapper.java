package com.iomc.forum.message.api.mapstruct;

import com.iomc.forum.message.api.dto.MessageDTO;
import com.iomc.forum.message.api.entity.Message;
import org.mapstruct.Mapper;

/**
 * <p>
 * 消息表
 * </p>
 *
 * @author song
 * @since 2024-12-12
 */
@Mapper
public interface MessageMapper {

    MessageMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(MessageMapper.class);

    Message toEntity (MessageDTO messageDTO);
}
