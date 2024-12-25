package com.iomc.forum.topic.api.mapstruct;

import com.iomc.forum.topic.api.dto.TagDTO;
import com.iomc.forum.topic.api.entity.Tag;
import com.iomc.forum.topic.api.vo.TagVO;
import org.mapstruct.Mapper;

@Mapper
public interface TagMapper {

    TagMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(TagMapper.class);

    TagVO toVO(Tag tag);

    Tag toEntity(TagDTO tagDTO);
}
