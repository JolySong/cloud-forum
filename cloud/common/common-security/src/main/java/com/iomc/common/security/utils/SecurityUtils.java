package com.iomc.common.security.utils;

import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.exception.ExceptionEnum;
import com.iomc.common.security.vo.LoginUserVO;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public static ThreadLocal<LoginUserVO> currentUser = new ThreadLocal<>();

    /**
     * 设置当前用户
     *
     * @param user
     */
    public static void setCurrentUser(LoginUserVO user) {
        clear();
        currentUser.set(user);
    }

    /**
     * 获取当前用户
     *
     * @return
     */
    public static LoginUserVO getCurrentUser() {
        return currentUser.get();
    }

    /**
     * 获取当前用户id
     *
     * @return
     */
    public static Long getCurrentUserId() {
        LoginUserVO loginUserVO = currentUser.get();
        if (loginUserVO == null) {
            throw new BizException(ExceptionEnum.UNAUTHORIZED);
        }

        return loginUserVO.getId();
    }

    /**
     * 是否管理员
     *
     * @return
     */
    public static boolean isAdmin() {
        LoginUserVO loginUserVO = currentUser.get();
        if (loginUserVO == null) {
            throw new BizException(ExceptionEnum.UNAUTHORIZED);
        }

        return loginUserVO.getRole().equals("admin");
    }

    /**
     * 是否登陆
     */
    public static boolean isLogin() {
        return currentUser.get() != null;
    }

    /**
     * 清除当前用户
     */
    public static void clear() {
        currentUser.remove();
    }
}
