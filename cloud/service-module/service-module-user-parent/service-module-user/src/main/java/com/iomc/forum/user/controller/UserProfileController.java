package com.iomc.forum.user.controller;


import com.iomc.common.core.Res;
import com.iomc.common.core.utils.StrUtils;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.analysis.api.feign.AnalysisServiceFeign;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import com.iomc.forum.file.api.dto.FileDTO;
import com.iomc.forum.file.api.feign.FileServiceFeign;
import com.iomc.forum.topic.api.feign.TopicServiceFeign;
import com.iomc.forum.user.api.dto.UserUpdateDTO;
import com.iomc.forum.user.service.UserProfileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 用户个人资料控制器
 */
@RestController
@RequestMapping("/user")
public class UserProfileController {


    private final UserProfileService userProfileService;
    private final TopicServiceFeign topicServiceFeign;
    private final AnalysisServiceFeign analysisServiceFeign;
    private final FileServiceFeign fileServiceFeign;

    public UserProfileController(UserProfileService userProfileService,
                                 TopicServiceFeign topicServiceFeign,
                                 AnalysisServiceFeign analysisServiceFeign,
                                 FileServiceFeign fileServiceFeign) {
        this.userProfileService = userProfileService;
        this.topicServiceFeign = topicServiceFeign;
        this.analysisServiceFeign = analysisServiceFeign;
        this.fileServiceFeign = fileServiceFeign;
    }

    /**
     * 获取登陆用户的个人资料
     *
     * @return
     */
    @GetMapping("/info")
    public Res getUserProfile() {
        return userProfileService.getMyProfile();
    }

    /**
     * 更新用户的个人资料
     *
     * @param dto
     * @return
     */
    @PutMapping("/info")
    public Res updateUserProfile(@RequestBody UserUpdateDTO dto) {

        if (null == dto) {
            return Res.fail("参数错误");
        }

        if (StrUtils.isNotBlank(dto.getNickname())
                || StrUtils.isNotBlank(dto.getAvatar())
                || StrUtils.isNotBlank(dto.getBio())
                || StrUtils.isNotBlank(dto.getEmail())) {
            return userProfileService.updateUserProfile(dto);
        }

        return Res.fail("参数错误");
    }

    /**
     * 更新用户的密码
     *
     * @param dto
     * @return
     */
    @PutMapping("/update/password")
    public Res updateUserPassword(@RequestBody UserUpdateDTO dto) {
        if (null == dto
                || StrUtils.isBlank(dto.getPassword())
                || StrUtils.isBlank(dto.getNewPassword())) {
            return Res.fail("参数错误");
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
    @GetMapping("/topics")
    public Res getMyTopics(Integer page, Integer size, String keyword) {
        return topicServiceFeign.getMyTopics(page, size, SecurityUtils.getCurrentUserId(), keyword);
    }


    /**
     * 获取用户的收藏列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/collects")
    public Res getMyCollects(Integer page, Integer size, String keyword) {
        return topicServiceFeign.getMyCollects(page, size, SecurityUtils.getCurrentUserId(), keyword);
    }

    /**
     * 获取用户的评论列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/comments")
    public Res getMyComments(Integer page, Integer size, String keyword) {
        return topicServiceFeign.getMyComments(page, size, SecurityUtils.getCurrentUserId(), keyword);
    }

    /**
     * 获取用户的点赞列表
     *
     * @param page
     * @param size
     * @param keyword
     * @return
     */
    @GetMapping("/likes")
    public Res getMyLikes(Integer page, Integer size, String keyword) {
        return topicServiceFeign.getMyLikes(page, size, SecurityUtils.getCurrentUserId(), keyword);
    }

    /**
     * 获取用户统计信息
     *
     */
    @GetMapping("/stat/{userId}")
    public Res getUserStat(@PathVariable(value = "userId") Long userId) {
        Res<UserStatVO> res = analysisServiceFeign.getUserStat(null, userId);
        return Res.success(res.getData());
    }

    /**
     * 头像上传
     *
     * @return
     */
    @PostMapping("/avatar")
    public Res uploadAvatar(@RequestParam("file") MultipartFile file) {

        if (file.isEmpty()) {
            return Res.fail("文件不能为空");
        }

        byte[] bytes = null;
        String originalFilename = file.getOriginalFilename();
        // 获取文件后缀名
        String suffix = "png";
        if (originalFilename != null) {
            suffix = originalFilename.split("\\.")[1];
        }

        try {
            bytes = file.getBytes();
        } catch (IOException e) {
            return Res.fail("文件获取失败, 请检查上传文件");
        }

        originalFilename = StrUtils.getRandomString(16) + "." + suffix;


        Res<String> upload = fileServiceFeign.upload(new FileDTO(originalFilename, bytes));
        if (!upload.getStatus() || StrUtils.isBlank(upload.getData())) {
            return Res.fail("头像上传失败");
        }

        Long currentUserId = SecurityUtils.getCurrentUserId();
        return Res.success(userProfileService.uploadAvatar(currentUserId, originalFilename));
    }

}
