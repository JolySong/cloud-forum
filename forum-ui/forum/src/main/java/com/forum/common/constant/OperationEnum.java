package com.forum.common.constant;

/**
 * 操作类型枚举
 */
public enum OperationEnum {

    CREATE("create"),

    UPDATE("update"),

    DELETE("delete"),

    LIKE("like"),

    COLLECT("collect");

    private final String value;

    OperationEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
