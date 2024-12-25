package com.iomc.common.security.vo;


import lombok.Data;

import java.util.Date;

@Data
public class UserVO {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 状态：0-禁用，1-正常
     */
    private Boolean status;

    /**
     * is_admin
     */
    private Boolean isSuperAdmin;
}
