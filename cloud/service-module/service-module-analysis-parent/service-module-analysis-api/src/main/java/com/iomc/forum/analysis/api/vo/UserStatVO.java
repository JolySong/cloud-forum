package com.iomc.forum.analysis.api.vo;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户统计信息
 */
@Data
public class UserStatVO {

    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 头像URL
     */
    private String avatar;
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickname;
    /** 简介 */
    private String bio;
    /** 注册时间 */
    private LocalDateTime createdAt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 是否关注
     */
    private Integer isFollowed;

    private Integer topicCount;     // 话题数
    private Integer commentCount;   // 评论数
    private Integer collectCount;   // 收藏数
    private Integer likeCount;      // 获赞数
    private Integer followCount;    // 关注数
    private Integer followerCount;  // 粉丝数
}