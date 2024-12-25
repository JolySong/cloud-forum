package com.forum.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.common.R;
import com.forum.common.utils.SecurityUtils;
import com.forum.dto.UserUpdateDTO;
import com.forum.mapper.UserProfileMapper;
import com.forum.service.UserProfileService;
import com.forum.vo.CommentVO;
import com.forum.vo.TopicVO;
import com.forum.vo.UserLikeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileServiceImpl implements UserProfileService {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UserProfileMapper userProfileMapper;


    /**
     * 查询我的个人资料
     */
    @Override
    public R getUserProfile() {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        return R.ok(userService.getUserInfo(currentUserId));
    }

    /**
     * 更新我的个人资料
     *
     * @param dto
     */
    @Override
    public R updateUserProfile(UserUpdateDTO dto) {
        Long currentUserId = SecurityUtils.getCurrentUserId();
        userService.updateUserInfo(currentUserId, dto);

        return R.ok();
    }

    /**
     * 更新我的密码
     *
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public R updateUserPassword(String oldPassword, String newPassword) {

        userService.updatePassword(SecurityUtils.getCurrentUserId(), oldPassword, newPassword);
        return R.ok();
    }

    /**
     * 查询我的话题
     *
     * @param page
     * @param size
     * @param keyword
     */
    @Override
    public R getMyTopics(Integer page, Integer size, String keyword) {

        IPage<TopicVO> topicVOIPage =
                userProfileMapper.selectTopicListByUserId(
                        Page.of(page, size), SecurityUtils.getCurrentUserId(), keyword);

        return R.ok(topicVOIPage);
    }

    /**
     * 查询我的回复
     *
     * @param page
     * @param size
     * @param keyword
     */
    @Override
    public R getMyComments(Integer page, Integer size, String keyword) {
        IPage<CommentVO> commentVOIPage =
                userProfileMapper.selectCommentListByUserId(
                        Page.of(page, size), SecurityUtils.getCurrentUserId(), keyword);

        return R.ok(commentVOIPage);
    }

    /**
     * 查询我的收藏
     *
     * @param page
     * @param size
     * @param keyword
     */
    @Override
    public R getMyCollects(Integer page, Integer size, String keyword) {
        IPage<TopicVO> topicVOIPage =
                userProfileMapper.selectCollectListByUserId(
                        Page.of(page, size), SecurityUtils.getCurrentUserId(), keyword);

        return R.ok(topicVOIPage);
    }

    /**
     * 查询我的点赞
     *
     * @param page
     * @param size
     * @param keyword
     */
    @Override
    public R getMyLikes(Integer page, Integer size, String keyword) {

        IPage<UserLikeVO> userLikeVOIPage =
                userProfileMapper.selectLikeListByUserId(
                        Page.of(page, size), SecurityUtils.getCurrentUserId(), keyword);


        return R.ok(userLikeVOIPage);
    }
}
