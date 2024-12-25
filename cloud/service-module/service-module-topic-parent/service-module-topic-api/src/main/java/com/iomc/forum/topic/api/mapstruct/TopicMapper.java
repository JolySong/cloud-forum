package com.iomc.forum.topic.api.mapstruct;

import com.iomc.forum.topic.api.dto.TopicDTO;
import com.iomc.forum.topic.api.entity.Topic;
import com.iomc.forum.topic.api.vo.TopicVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TopicMapper {

    TopicMapper INSTANCE = Mappers.getMapper(TopicMapper.class);

    TopicVO toVO(Topic topic);

    Topic toEntity(TopicDTO topicDTO);
}
