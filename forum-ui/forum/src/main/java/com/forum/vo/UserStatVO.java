package com.forum.vo;

import lombok.Data;

@Data
public class UserStatVO {
    private Integer topicCount;    // 话题数
    private Integer commentCount;   // 评论数
    private Integer collectCount;   // 收藏数
    private Integer likeCount;      // 获赞数
    private Integer followCount;    // 关注数
    private Integer followerCount;  // 粉丝数
} 