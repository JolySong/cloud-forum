package com.iomc.common.security.dto;


import lombok.Data;

@Data
public class LoginUserDTO {

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /** 验证码 */
    private String code;

    /** 邮箱 */
    private String email;

    /** 设备 */
    private String device;
}
