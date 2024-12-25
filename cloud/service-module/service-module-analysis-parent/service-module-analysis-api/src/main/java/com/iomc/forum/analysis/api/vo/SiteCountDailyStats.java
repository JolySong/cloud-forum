package com.iomc.forum.analysis.api.vo;

import lombok.Data;

/**
 * 站点统计
 */
@Data
public class SiteCountDailyStats {
    /**
     * 日期
     */
    private String date;
    /**
     * 主题数
     */
    private Integer topicCount;
    /**
     * 评论数
     */
    private Integer commentCount;
}
