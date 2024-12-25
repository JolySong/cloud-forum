package com.forum.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserLikeVO {
    private Long likeId;           // 点赞ID
    private LocalDateTime likeTime; // 点赞时间
    private String targetType;      // 点赞类型 1:话题 2:评论

    private Long targetId;         // 目标ID（话题ID或评论ID）
    private String title;          // 标题（话题标题或评论内容）
    private String content;        // 内容（话题内容，评论时为null）
    private Integer viewCount;     // 浏览量（仅话题有）
    private Integer likeCount;     // 点赞数
    private Integer commentCount;  // 评论数（仅话题有）
    private LocalDateTime targetTime; // 目标创建时间

    private Long authorId;         // 作者ID
    private String authorName;     // 作者昵称
    private String authorAvatar;   // 作者头像

    // 评论相关（仅评论点赞有）
    private Long topicId;          // 原话题ID
    private String topicTitle;     // 原话题标题
}