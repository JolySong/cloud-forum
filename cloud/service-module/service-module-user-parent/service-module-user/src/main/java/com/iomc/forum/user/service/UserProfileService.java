package com.iomc.forum.user.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.iomc.common.core.Res;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.user.api.dto.UserUpdateDTO;
import com.iomc.forum.user.api.entity.User;
import com.iomc.forum.user.api.vo.UserProfileVO;

public interface UserProfileService extends IService<User> {

    /**
     * 查询登陆用户信息
     *
     * @param username
     * @return
     */
    LoginUserVO getUserByUsername(String username);

    /**
     * 查询我的个人资料
     */
    Res getMyProfile();

    /**
     * 查询用户个人资料
     *
     * @param userId
     */
    UserProfileVO getUserProfile(Long userId);

    /**
     * 更新我的个人资料
     *
     * @param dto
     */
    Res updateUserProfile(UserUpdateDTO dto);

    /**
     * 更新我的密码
     * @param oldPassword
     * @param newPassword
     */
    Res updateUserPassword(String oldPassword, String newPassword);

    /**
     *
     * 重置密码
     * @param email
     * @param password
     *  */
    Res<String> resetPassword(String email, String password);

    /**
     * 上传头像
     *
     * @param userId
     * @param filePath
     * */
    Res uploadAvatar(Long userId, String filePath);
}
