package com.iomc.auth.dao;

import com.iomc.auth.entity.SysConfig;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysConfigDao extends SpiceBaseMapper<SysConfig> {
}
