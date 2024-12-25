package com.iomc.forum.user.api.feign;


import com.iomc.common.core.Res;
import com.iomc.common.security.dto.LoginUserDTO;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.user.api.fallback.UserServiceFeignFallbackFactory;
import com.iomc.forum.user.api.vo.UserProfileVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户服务接口
 */
@FeignClient(contextId = "adminServiceFeign", value = "service-module-user",
        fallbackFactory = UserServiceFeignFallbackFactory.class)
public interface UserServiceFeign {


    /**
     * 根据用户名获取登陆用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/inner/user/getUserByUsername/{username}")
    Res<LoginUserVO> getLoginUserByUsername(@PathVariable(value = "username") String username);

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/inner/user/getUserProfile/{userId}")
    Res<UserProfileVO> getUserProfile(@PathVariable(value = "userId") Long userId);

    /**
     * 重置密码
     *
     * @param loginUserDTO
     */
    @PostMapping("/inner/user/resetPassword")
    Res resetPassword(@RequestBody LoginUserDTO loginUserDTO);

}
