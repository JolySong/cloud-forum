package com.iomc.forum.topic.api.mapstruct;

import com.iomc.forum.topic.api.dto.CategoryDTO;
import com.iomc.forum.topic.api.entity.Category;
import com.iomc.forum.topic.api.vo.CategoryVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(CategoryMapper.class);

    /**
     * 实体类转VO
     * @param category
     * @return
     */
    CategoryVO toVO(Category category);

    /**
     * DTO转ENTITY
     * @param categoryDTO
     */
    Category toEntity(CategoryDTO categoryDTO);
}
