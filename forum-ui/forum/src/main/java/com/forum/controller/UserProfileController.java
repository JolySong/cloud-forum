package com.forum.controller;


import com.forum.common.R;
import com.forum.common.utils.StrUtil;
import com.forum.dto.UserUpdateDTO;
import com.forum.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserProfileController {


    @Autowired
    private UserProfileService userProfileService;

    /**
     * 获取用户的个人资料
     *
     * @return
     */
    @GetMapping("/user/info")
    public R getUserProfile() {
        return userProfileService.getUserProfile();
    }

    /**
     * 更新用户的个人资料
     *
     * @param dto
     * @return
     */
    @PutMapping("/user/info")
    public R updateUserProfile(@RequestBody UserUpdateDTO dto) {

        if (null == dto) {
            return R.error("参数错误");
        }

        if (StrUtil.isNotBlank(dto.getNickname())
                || StrUtil.isNotBlank(dto.getAvatar())
                || StrUtil.isNotBlank(dto.getBio())
                || StrUtil.isNotBlank(dto.getEmail())) {
            return userProfileService.updateUserProfile(dto);
        }

        return R.error("参数错误");
    }

    /**
     * 更新用户的密码
     *
     * @param dto
     * @return
     */
    @PutMapping("/user/update/password")
    public R updateUserPassword(@RequestBody UserUpdateDTO dto) {
        if (null == dto
                || StrUtil.isBlank(dto.getPassword())
                || StrUtil.isBlank(dto.getNewPassword())) {
            return R.error("参数错误");
        }

        return userProfileService.updateUserPassword(dto.getPassword(), dto.getNewPassword());
    }

    /**
     * 获取用户的主题列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/user/topics")
    public R getMyTopics(Integer page, Integer size, String keyword) {
        return userProfileService.getMyTopics(page, size, keyword);
    }


    /**
     * 获取用户的收藏列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/user/collects")
    public R getMyCollects(Integer page, Integer size, String keyword) {
        return userProfileService.getMyCollects(page, size, keyword);
    }

    /**
     * 获取用户的评论列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/user/comments")
    public R getMyComments(Integer page, Integer size, String keyword) {
        return userProfileService.getMyComments(page, size, keyword);
    }

    /**
     * 获取用户的点赞列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/user/likes")
    public R getMyLikes(Integer page, Integer size, String keyword) {
        return userProfileService.getMyLikes(page, size, keyword);
    }

}
