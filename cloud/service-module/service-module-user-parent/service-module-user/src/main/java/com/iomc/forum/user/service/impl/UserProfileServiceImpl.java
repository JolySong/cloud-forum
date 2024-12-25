package com.iomc.forum.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.iomc.common.core.Res;
import com.iomc.common.core.exception.BizException;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.common.security.vo.LoginUserVO;
import com.iomc.forum.user.api.dto.UserUpdateDTO;
import com.iomc.forum.user.api.entity.User;
import com.iomc.forum.user.api.mapstruct.UserProfileMapper;
import com.iomc.forum.user.api.vo.UserProfileVO;
import com.iomc.forum.user.dao.UserProfileDao;
import com.iomc.forum.user.service.UserProfileService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * 用户资料服务实现类
 */
@Service
public class UserProfileServiceImpl extends ServiceImpl<UserProfileDao, User> implements UserProfileService {


    /**
     * 根据用户名获取登陆用户信息
     *
     * @param username
     * @return
     */
    @Override
    public LoginUserVO getUserByUsername(String username) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
        if (user == null) {
            return null;
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }

    /**
     * 查询用户资料
     *
     */
    @Override
    public Res getMyProfile() {

        Long userId = SecurityUtils.getCurrentUserId();
        User user = getById(userId);
        if (user == null) {
            return Res.fail("用户不存在");
        }

        UserProfileVO userVO = new UserProfileVO();
        BeanUtils.copyProperties(user, userVO);

        return Res.success(userVO);
    }

    /**
     * 查询用户资料
     *
     * @param userId
     */
    @Override
    public UserProfileVO getUserProfile(Long userId) {

        User user = getById(userId);
        if (user == null) {
            throw new BizException("用户不存在");
        }

        return UserProfileMapper.INSTANCE.toVO(user);
    }

    /**
     * 更新我的个人资料
     *
     * @param dto
     */
    @Override
    public Res updateUserProfile(UserUpdateDTO dto) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = getById(currentUserId);
        if (user == null) {
            return Res.fail("用户不存在");
        }

        // 检查昵称是否已被使用
        if (!user.getNickname().equals(dto.getNickname())) {
            Long count = baseMapper.selectCount(new QueryWrapper<User>().eq("nickname", dto.getNickname()));
            if (count > 0) {
                return Res.fail("昵称已被使用");
            }
        }

        // 更新用户信息
        int cnt = baseMapper.update(
                new LambdaUpdateWrapper<User>()
                        .set(StrUtils.isNotBlank(dto.getNickname()), User::getNickname, dto.getNickname())
                        .set(StrUtils.isNotBlank(dto.getEmail()), User::getEmail, dto.getEmail())
                        .set(StrUtils.isNotBlank(dto.getAvatar()), User::getAvatar, dto.getAvatar())
                        .set(StrUtils.isNotBlank(dto.getBio()), User::getBio, dto.getBio())
                .eq(User::getId, currentUserId)
        );

        return Res.success(cnt);
    }

    /**
     * 更新我的密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public Res updateUserPassword(String oldPassword, String newPassword) {

        if (newPassword.length() < 6) {
            return Res.fail("密码长度不能小于6位");
        }
        Long currentUserId = SecurityUtils.getCurrentUserId();
        User user = getById(currentUserId);
        if (user == null) {
            return Res.fail("用户不存在");
        }
        if (!user.getPassword().equals(oldPassword)) {
            return Res.fail("原密码错误");
        }
        if (newPassword.equals(oldPassword)) {
            return Res.fail("新密码与旧密码一致");
        }

        int cnt = baseMapper.update(
                new LambdaUpdateWrapper<User>()
                        .set(StrUtils.isNotBlank(newPassword), User::getPassword, newPassword)
                        .eq(User::getId, currentUserId)
        );

        return Res.success(cnt);
    }

    /**
     * 重置密码
     *
     * @param email
     * @param password
     */
    @Override
    public Res<String> resetPassword(String email, String password) {

        if (password.length() < 6) {
            return Res.fail("密码长度不能小于6位");
        }

        User user = baseMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getEmail, email));

        if (null == user) {
            return Res.fail("用户不存在");
        }

        if (password.equals(user.getPassword())) {
            return Res.fail("新密码与旧密码一致");
        }

        user.setPassword(password);
        updateById(user);


        return Res.success("修改成功");
    }

    /**
     * 上传头像
     *
     * @param userId
     * @param filePath
     */
    @Override
    public Res uploadAvatar(Long userId, String filePath) {

        int i =baseMapper.update(
                new LambdaUpdateWrapper<User>()
                        .set(User::getAvatar, filePath)
                        .eq(User::getId, userId)
        );

        return Res.success(i);
    }
}
