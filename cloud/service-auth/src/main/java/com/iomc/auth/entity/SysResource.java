package com.iomc.auth.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 资源权限表
 * </p>
 *
 * @author song
 * @since 2024-12-09
 */
@Data
@TableName("sys_resource")
public class SysResource implements Serializable {

    /**
     * 资源ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 父资源ID
     */
    @TableField("parent_id")
    private Long parentId;

    /**
     * 资源名称
     */
    @TableField("resource_name")
    private String resourceName;

    /**
     * 资源类型（M目录 C菜单 F按钮 A接口）
     */
    @TableField("resource_type")
    private String resourceType;

    /**
     * 资源权限标识
     */
    @TableField("resource_key")
    private String resourceKey;

    /**
     * 前端路由地址
     */
    @TableField("resource_url")
    private String resourceUrl;

    /**
     * 接口地址
     */
    @TableField("api_url")
    private String apiUrl;

    /**
     * 请求方法（GET POST PUT DELETE）
     */
    @TableField("method")
    private String method;

    /**
     * 组件路径
     */
    @TableField("component")
    private String component;

    /**
     * 显示顺序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 图标
     */
    @TableField("icon")
    private String icon;

    /**
     * 资源状态（0显示 1隐藏）
     */
    @TableField("status")
    private Boolean status;

    /**
     * 是否公开资源(不需要权限认证) 0否 1是
     */
    @TableField("is_public")
    private Boolean isPublic;

    /**
     * 创建时间
     */
    @TableField("created_at")
    private LocalDateTime createdAt;

    /**
     * 更新时间
     */
    @TableField("updated_at")
    private LocalDateTime updatedAt;

    /**
     * 删除标志（0代表存在 1代表删除）
     */
    @TableField("deleted")
    private Boolean deleted;
}
