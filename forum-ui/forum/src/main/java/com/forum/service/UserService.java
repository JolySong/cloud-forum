package com.forum.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.entity.User;
import com.forum.vo.UserStatVO;
import com.forum.vo.UserVO;
import com.forum.dto.UserUpdateDTO;

public interface UserService extends IService<User> {
    /**
     * 获取用户信息
     */
    UserVO getUserInfo(Long userId);

    /**
     * 更新用户信息
     */
    void updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO);

    /**
     * 更新用户头像
     */
    String updateAvatar(Long userId, String avatarUrl);

    /**
     * 修改密码
     */
    void updatePassword(Long userId, String oldPassword, String newPassword);

    /**
     * 获取用户统计信息
     */
    UserStatVO getUserStats(Long userId);
} 