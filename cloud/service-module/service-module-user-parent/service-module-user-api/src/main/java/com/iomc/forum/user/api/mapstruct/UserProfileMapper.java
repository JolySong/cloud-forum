package com.iomc.forum.user.api.mapstruct;

import com.iomc.forum.user.api.entity.User;
import com.iomc.forum.user.api.vo.UserProfileVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserProfileMapper {

    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfileVO toVO(User user);
}
