package com.iomc.auth.dao;

import com.iomc.auth.entity.SysRole;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 角色信息表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Mapper
public interface SysRoleDao extends SpiceBaseMapper<SysRole> {

}
