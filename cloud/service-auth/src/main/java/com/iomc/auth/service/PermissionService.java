package com.iomc.auth.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.iomc.auth.entity.SysResource;
import com.iomc.auth.entity.SysRoleResource;
import com.iomc.auth.entity.SysUserRole;
import com.iomc.common.core.utils.ListUtils;
import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.common.security.constant.Const;
import com.iomc.common.security.vo.ResourceVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PermissionService {

    @Resource
    private ISysUserRoleService sysUserRoleService;

    @Resource
    private ISysRoleResourceService sysRoleResourceService;

    @Resource
    private ISysResourceService sysResourceService;


    /**
     * 加载用户权限到Redis
     */
    public void loadUserPermissions(Long userId) {
        // 获取用户角色
        List<SysUserRole> sysUserRoles =
                sysUserRoleService.list(
                        new LambdaQueryWrapper<SysUserRole>()
                                .eq(SysUserRole::getUserId, userId));

        // 查询角色拥有的资源id
        List<Long> roleIds = sysUserRoles.stream()
                .map(SysUserRole::getRoleId)
                .collect(Collectors.toList());
        List<SysRoleResource> sysRoleResources =
                sysRoleResourceService.list(
                        new LambdaQueryWrapper<SysRoleResource>()
                        .in(SysRoleResource::getRoleId, roleIds));

        // 查询资源
        List<Long> resourceIds = sysRoleResources.stream()
                .map(SysRoleResource::getResourceId)
                .collect(Collectors.toList());
        List<SysResource> sysResources =
                sysResourceService.list(
                        new LambdaQueryWrapper<SysResource>()
                                .eq(SysResource::getDeleted, 0)
                                .eq(SysResource::getIsPublic, 0)
                                .eq(SysResource::getResourceType, "A")
                                .in(SysResource::getId, resourceIds));

        if (ListUtils.isNotEmpty(sysResources)) {
            List<ResourceVO> resourceVOS = new ArrayList<>();
            sysResources.forEach(resource ->
                resourceVOS.add(new ResourceVO(resource.getApiUrl(), resource.getMethod())));

            RedisUtil.set(Const.USER_PERMISSIONS_KEY + userId, resourceVOS, 24 * 60 * 60 * 1000L);
        }
    }

    /**
     * 刷新权限缓存
     */
    public void refreshPermissionCache(Long userId) {
        String key = Const.USER_PERMISSIONS_KEY + userId;
        RedisUtil.delete(key);
        loadUserPermissions(userId);
    }
}