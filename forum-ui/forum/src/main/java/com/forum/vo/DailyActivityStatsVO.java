package com.forum.vo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyActivityStatsVO {
    private LocalDate date;         // 日期
    private Integer newUsers;       // 新用户数
    private Integer newTopics;      // 新话题数
    private Integer newComments;    // 新评论数
    private Integer activeUsers;    // 活跃用户数
    private Integer totalViews;     // 总浏览量
    private Integer newLikes;       // 新增点赞数
    private Integer activityScore;  // 活跃度得分
}
