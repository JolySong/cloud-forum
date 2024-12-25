package com.iomc.forum.user.dao;

import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import com.iomc.forum.user.api.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserProfileDao extends SpiceBaseMapper<User> {

}
