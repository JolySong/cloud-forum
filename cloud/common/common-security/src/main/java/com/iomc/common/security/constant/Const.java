package com.iomc.common.security.constant;

public interface Const {

    /**
     * jwt验证通过请求头
     */
    String JWT_PAYLOAD_TOKEN = "jwt_payload_token";

    /**
     * 用户权限key
     */
    String USER_PERMISSIONS_KEY = "user:permissions:";

    /**
     * 公共资源key
     */
    String PUBLIC_RESOURCES_KEY = "public:resources";

    /**
     * 请求用户id
     */
    String REQUEST_USER_ID = "request_user_id";


    /**
     * 系统配置key
     */
    String SYSTEM_CONFIG_KEY = "system:config:";

    /**
     * 单设备登录
     */
    String SINGLE_LOGIN = "singleDeviceLogin";

}
