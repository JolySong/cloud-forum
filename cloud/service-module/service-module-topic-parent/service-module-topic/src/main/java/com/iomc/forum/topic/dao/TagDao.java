package com.iomc.forum.topic.dao;

import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.topic.api.entity.Tag;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagDao extends SpiceBaseMapper<Tag> {
}
