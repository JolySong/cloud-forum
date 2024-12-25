package com.iomc.forum.user.api.fallback;


import com.iomc.common.core.Res;
import com.iomc.common.security.dto.LoginUserDTO;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.user.api.feign.UserServiceFeign;
import com.iomc.forum.user.api.vo.UserProfileVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 用户服务调用失败的降级处理
 */
@Component
public class UserServiceFeignFallbackFactory implements FallbackFactory<UserServiceFeign> {

    private static final Logger log =
            LoggerFactory.getLogger(UserServiceFeignFallbackFactory.class);

    @Override
    public UserServiceFeign create(Throwable throwable) {

        return new UserServiceFeign() {

            /**
             * 根据用户名获取用户信息
             *
             * @param username
             * @return
             */
            @Override
            public Res<LoginUserVO> getLoginUserByUsername(String username) {
                log.error("用户服务->getLoginUserByUsername()->调用失败:{}", throwable.getMessage());
                return null;
            }

            /**
             * 根据id获取用户信息
             *
             * @param userId
             * @return
             */
            @Override
            public Res<UserProfileVO> getUserProfile(Long userId) {
                log.error("用户服务->getUserProfile()->调用失败:{}", throwable.getMessage());
                return null;
            }

            /**
             * 重置密码
             *
             * @param loginUserDTO
             */
            @Override
            public Res resetPassword(LoginUserDTO loginUserDTO) {
                log.error("用户服务->resetPassword()->调用失败:{}", throwable.getMessage());
                return null;
            }


        };
    }
}
