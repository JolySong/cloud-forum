package com.iomc.forum.message.api.mapstruct;

import com.iomc.forum.message.api.dto.EmailSendDTO;
import com.iomc.forum.message.api.entity.EmailSendRecords;
import com.iomc.forum.message.api.vo.EmailSendVO;
import org.mapstruct.Mapper;

@Mapper
public interface EmailSendMapper {

    EmailSendMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(EmailSendMapper.class);

    EmailSendRecords toEntity (EmailSendDTO emailSendDTO);

    EmailSendVO toVO (EmailSendRecords emailSendRecords);
}
