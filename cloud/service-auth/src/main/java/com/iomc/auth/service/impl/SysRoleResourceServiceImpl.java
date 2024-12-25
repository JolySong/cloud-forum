package com.iomc.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.auth.dao.SysRoleResourceDao;
import com.iomc.auth.entity.SysRoleResource;
import com.iomc.auth.service.ISysRoleResourceService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和资源关联表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Service
public class SysRoleResourceServiceImpl extends ServiceImpl<SysRoleResourceDao, SysRoleResource> implements ISysRoleResourceService {

}
