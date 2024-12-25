package com.iomc.auth.dao;

import com.iomc.auth.entity.SysUserRole;
import com.iomc.common.mybatis.mapper.SpiceBaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 用户和角色关联表 Mapper 接口
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Mapper
public interface SysUserRoleDao extends SpiceBaseMapper<SysUserRole> {

}
