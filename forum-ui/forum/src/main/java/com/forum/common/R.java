package com.forum.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应对象
 *
 * @author forum
 * @since 2024-03-15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class R<T> {
    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 成功响应
     *
     * @param data 响应数据
     * @return 响应对象
     */
    public static <T> R<T> ok(T data) {
        return new R<>(200, "success", data);
    }

    /**
     * 成功响应（无数据）
     *
     * @return 响应对象
     */
    public static <T> R<T> ok() {
        return ok(null);
    }

    /**
     * 错误响应
     *
     * @param message 错误信息
     * @return 响应对象
     */
    public static <T> R<T> error(String message) {
        return new R<>(500, message, null);
    }
} 