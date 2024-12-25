package com.iomc.auth.dao;

import com.iomc.auth.entity.SysRoleResource;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色和资源关联表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Mapper
public interface SysRoleResourceDao extends SpiceBaseMapper<SysRoleResource> {

}
