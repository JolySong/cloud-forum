package com.iomc.common.core;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 公共返回类
 *
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Res<T> {

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 提示消息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp = System.currentTimeMillis();


    /**
     * 返回成功
     *
     * @return
     * @param <T>
     */
    public static <T> Res<T> success() {
        return success(null, null);
    }


    /**
     * 返回成功
     *
     * @param data
     * @return
     * @param <T>
     */
    public static <T> Res<T> success(T data) {
        return success(data, null);
    }

    /**
     * 返回成功
     *
     * @param data
     * @param message
     * @return
     * @param <T>
     */
    public static <T> Res<T> success(T data, String message) {
        Res res = new Res();
        res.setCode(ResEnum.SUCCESS.code);
        res.setStatus(true);
        res.setData(data);
        res.setMessage(message);
        return res;
    }

    /**
     * 失败
     *
     * @param msg 提示消息
     * @return
     * @param <T>
     */
    public static <T> Res<T> fail(String msg) {
        return fail(ResEnum.FAIL.code, msg);
    }

    /**
     * 失败
     *
     * @param code 编码
     * @param message 信息
     * @return
     */
    public static Res fail(int code, String message){
        Res res = new Res();
        res.setCode(code);
        res.setMessage(message);
        res.setStatus(false);
        return res;
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", timestamp=" + timestamp +
                '}';
    }

    public enum ResEnum {

        SUCCESS(200, "成功"),
        FAIL(400, "参数列表错误（缺少，格式不匹配"),
        UNAUTHORIZED(401, "未经授权"),
        FORBIDDEN(403, "访问受限，授权过期"),
        NOT_FOUND(404, "资源，服务未找！"),
        BAD_METHOD(405, "不允许的http方法"),
        UNSUPPORTED_TYPE(415, "不支持的数据，媒体类型"),
        INTERNAL_SERVER_ERROR(500, "系统内部错误"),
        NOT_IMPLEMENTED(501, "接口未实现"),
        NOT_INSTANCE(503, "服务不存在");

        public final int code;
        public final String msg;
        ResEnum(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }
    }
}


