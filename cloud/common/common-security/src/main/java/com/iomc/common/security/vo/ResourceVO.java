package com.iomc.common.security.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResourceVO {

    /**
     * 接口地址
     */
    private String apiUrl;

    /**
     * 请求方法（GET POST PUT DELETE）
     */
    private String method;
}
