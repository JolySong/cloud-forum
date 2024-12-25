package com.forum.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.forum.common.exception.BusinessException;
import com.forum.entity.User;
import com.forum.mapper.UserMapper;
import com.forum.service.UserService;
import com.forum.vo.UserVO;
import com.forum.vo.UserStatVO;
import com.forum.dto.UserUpdateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private final UserMapper userMapper;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUserInfo(Long userId, UserUpdateDTO userUpdateDTO) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 检查昵称是否已被使用
        if (!user.getNickname().equals(userUpdateDTO.getNickname())) {
            int count = userMapper.countByNickname(userUpdateDTO.getNickname());
            if (count > 0) {
                throw new BusinessException("昵称已被使用");
            }
        }

        // 更新用户信息
        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setNickname(userUpdateDTO.getNickname());
        updateUser.setEmail(userUpdateDTO.getEmail());
        updateUser.setBio(userUpdateDTO.getBio());
        updateById(updateUser);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String updateAvatar(Long userId, String avatarUrl) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        User updateUser = new User();
        updateUser.setId(userId);
        updateUser.setAvatar(avatarUrl);
        updateById(updateUser);

        return avatarUrl;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = getById(userId);
        if (user == null) {
            throw new BusinessException("用户不存在");
        }

        // 验证旧密码
//        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
//            throw new BusinessException("原密码错误");
//        }

        // 更新密码
        User updateUser = new User();
        updateUser.setId(userId);
//        updateUser.setPassword(passwordEncoder.encode(newPassword));
//        updateById(updateUser);
    }

    @Override
    public UserStatVO getUserStats(Long userId) {
        return userMapper.selectUserStats(userId);
    }
} 