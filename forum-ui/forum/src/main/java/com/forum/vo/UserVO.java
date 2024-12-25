package com.forum.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVO {
    private Long id;
    private String username;
    private String nickname;
    private String avatar;
    private String email;
    private String bio;
    private String role;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;

    private Integer likeCount;
    private Integer commentCount;
    private Integer topicCount;
    private Integer totalViews;
    private Integer totalTopicLikes;
    private Integer totalCommentLikes;
    private Integer recentComments;
    private Integer recentTopics;
    private Integer commentLikesReceived;
} 