package com.iomc.forum.message.dao;

import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.message.api.entity.EmailSendRecords;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 邮件记录表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-11
 */
@Mapper
public interface EmailSendRecordsDao extends SpiceBaseMapper<EmailSendRecords> {

}
