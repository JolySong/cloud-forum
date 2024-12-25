package com.forum.common.utils;

//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;

/**
 * 安全工具类
 *
 * @author forum
 * @since 2024-03-15
 */
public class SecurityUtils {

    /**
     * 获取当前登录用户ID
     *
     * @return 用户ID
     */
    public static Long getCurrentUserId() {

        return 6L;
//        UserDetails userDetails = getCurrentUser();
//        if (userDetails instanceof LoginUser) {
//            return ((LoginUser) userDetails).getId();
//        }
//        return null;
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
//    public static UserDetails getCurrentUser() {
//        Authentication authentication = getAuthentication();
//        if (authentication == null) {
//            throw new BusinessException("用户未登录");
//        }
//
//        Object principal = authentication.getPrincipal();
//        if (principal instanceof UserDetails) {
//            return (UserDetails) principal;
//        }
//        throw new BusinessException("获取用户信息失败");
//    }

    /**
     * 获取Authentication
     */
//    public static Authentication getAuthentication() {
//        return SecurityContextHolder.getContext().getAuthentication();
//    }

    /**
     * 判断是否为管理员
     *
     * @return true:是 false:否
     */
    public static boolean isAdmin() {

        return false;
//        UserDetails user = getCurrentUser();
//        return user.getAuthorities().stream()
//                .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
    }

    /**
     * 判断是否已登录
     *
     * @return true:已登录 false:未登录
     */
//    public static boolean isAuthenticated() {
//        Authentication authentication = getAuthentication();
//        return authentication != null && authentication.isAuthenticated() &&
//                !"anonymousUser".equals(authentication.getPrincipal());
//    }
}