package com.iomc.auth.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 角色和资源关联表
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Data
@TableName("sys_role_resource")
public class SysRoleResource implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 角色ID
     */
    @TableField(value = "role_id")
    private Long roleId;

    /**
     * 资源ID
     */
    @TableField(value = "resource_id")
    private Long resourceId;
}
