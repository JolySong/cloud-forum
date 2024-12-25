package com.iomc.forum.user.controller;


import com.iomc.common.core.Res;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.dto.LoginUserDTO;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.user.api.vo.UserProfileVO;
import com.iomc.forum.user.service.impl.UserProfileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class InnerController {

    @Autowired
    private UserProfileServiceImpl userProfileService;

    /**
     * 根据用户名获取用户信息
     *
     * @param username
     * @return
     */
    @GetMapping("/inner/user/getUserByUsername/{username}")
    public Res<LoginUserVO> getUserByUsername(@PathVariable(value = "username") String username) {
        return Res.success(userProfileService.getUserByUsername(username));
    }

    /**
     * 根据id获取用户信息
     *
     * @param userId
     * @return
     */
    @GetMapping("/inner/user/getUserProfile/{userId}")
    Res<UserProfileVO> getUserProfile(@PathVariable(value = "userId") Long userId) {
        if (userId == null) {
            return null;
        }

        return Res.success(userProfileService.getUserProfile(userId));
    }

    /**
     * 重置密码
     *
     * @param loginUserDTO
     */
    @PostMapping("/inner/user/resetPassword")
    public Res<String> resetPassword(@RequestBody LoginUserDTO loginUserDTO) {
        if (null == loginUserDTO || StrUtils.isBlank(loginUserDTO.getEmail())
                || StrUtils.isBlank(loginUserDTO.getPassword())) {
            return Res.fail("参数错误");
        }

        return userProfileService.resetPassword(loginUserDTO.getEmail(), loginUserDTO.getPassword());

    }
}
