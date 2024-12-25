package com.iomc.gateway.service;

import com.iomc.common.redis.utils.RedisUtil;
import com.iomc.common.security.constant.Const;
import com.iomc.common.security.vo.ResourceVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 资源服务
 */
@Slf4j
public class ResourceService {


    /**
     * 判断是否公开资源
     *
     * @param url
     * @return
     */
    public static boolean isPublicResource(String url) {

        boolean defaultReturn = false;
        Object o = RedisUtil.get(Const.PUBLIC_RESOURCES_KEY);
        if (null == o) {
            log.error("Redis中存储的公开资源为空，请检查");
            return defaultReturn;
        }

        if (o instanceof Set<?>) {
            @SuppressWarnings("unchecked")
            Set<String> publicResources = (Set<String>) o;
            for (String publicResource : publicResources) {
                if (matchesWithDynamicParameters(publicResource, url)) {
                    return true;
                }
            }
        }

        return defaultReturn;
    }

    /**
     * 匹配用户资源
     *
     * @param userId
     * @param requestResource
     */
    public static boolean matchUserResources(String userId, ResourceVO requestResource) {

        boolean defaultReturn = false;

        Object o = RedisUtil.get(Const.USER_PERMISSIONS_KEY + userId);
        if (null == o) {
            log.error("Redis中存储的用户资源为空，请检查");
            return defaultReturn;
        }

        if (o instanceof List<?>) {
            @SuppressWarnings("unchecked")
            List<ResourceVO> userResources = (List<ResourceVO>) o;
            for (ResourceVO userResource : userResources) {
                if (!userResource.getMethod().equals(requestResource.getMethod())) {
                    continue;
                }

                if (matchesWithDynamicParameters(userResource.getApiUrl(), requestResource.getApiUrl())) {
                    return true;
                }
            }
        }

        return defaultReturn;
    }

    /**
     * 使用正则表达式匹配路径中的动态参数
     *
     * @param storedPath 存储的路径
     * @param requestPath 请求的路径
     * @return 是否匹配
     */
    private static boolean matchesWithDynamicParameters(String storedPath, String requestPath) {
        if (storedPath.equals(requestPath)) {
            return true;
        }

        if (!storedPath.contains("{")) {
            return false;
        }
        // 将 {id} 替换为正则表达式 \d+
        String pattern = storedPath.replaceAll("\\{\\w+}", "[^/]+");
        return Pattern.matches(pattern, requestPath);
    }
}
