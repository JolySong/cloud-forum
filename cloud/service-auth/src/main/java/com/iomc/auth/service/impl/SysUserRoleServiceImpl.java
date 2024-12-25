package com.iomc.auth.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.auth.dao.SysUserRoleDao;
import com.iomc.auth.entity.SysUserRole;
import com.iomc.auth.service.ISysUserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联表 服务实现类
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleDao, SysUserRole> implements ISysUserRoleService {

}
