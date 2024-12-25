package com.iomc.common.core.utils;

import java.util.List;

/**
 * 集合工具类
 */
public class ListUtils {

    /**
     * 判断数组是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(Object[] array) {
        return (array == null || array.length == 0);
    }

    /**
     * 判断list是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(Object[] array) {
        return !isEmpty(array);
    }

    /**
     * 判断list是否为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(List array) {
        return (array == null || array.isEmpty());
    }

    /**
     * 判断list是否不为空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(List array) {
        return !isEmpty(array);
    }
}
