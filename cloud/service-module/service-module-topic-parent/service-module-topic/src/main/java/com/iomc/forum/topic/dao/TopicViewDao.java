package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.iomc.forum.topic.api.entity.TopicView;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TopicViewDao extends BaseMapper<TopicView> {
}
