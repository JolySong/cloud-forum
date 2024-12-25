package com.iomc.common.security.vo;

import lombok.Data;

@Data
public class LoginUserVO {

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 角色
     */
    private String role;

    /** 设备 */
    private String device;
}
