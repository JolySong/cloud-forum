package com.forum.service;


import com.forum.common.R;
import com.forum.dto.UserUpdateDTO;

public interface UserProfileService {

    /**
     * 查询我的个人资料
     */
    R getUserProfile();

    /**
     * 更新我的个人资料
     *
     * @param dto
     */
    R updateUserProfile(UserUpdateDTO dto);

    /**
     * 更新我的密码
     */
    R updateUserPassword(String oldPassword, String newPassword);

    /**
     * 查询我的话题
     *
     * @param page
     * @param size
     * @param keyword
     */
    R getMyTopics(Integer page, Integer size, String keyword);

    /**
     * 查询我的回复
     *
     * @param page
     * @param size
     * @param keyword
     */
    R getMyComments(Integer page, Integer size, String keyword);

    /**
     * 查询我的收藏
     *
     * @param page
     * @param size
     * @param keyword
     */
    R getMyCollects(Integer page, Integer size, String keyword);

    /**
     * 查询我的点赞
     *
     * @param page
     * @param size
     * @param keyword
     */
    R getMyLikes(Integer page, Integer size, String keyword);
}
