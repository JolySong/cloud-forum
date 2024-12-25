package com.iomc.forum.analysis.api.vo;

import lombok.Data;

import java.util.Map;

/**
 * 站点统计视图对象
 */
@Data
public class SiteCountVO {
    /**
     * 用户数量
     */
    private Integer userCount;

    /**
     * 主题数量
     */
    private Integer topicCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 浏览量
     */
    private Integer viewCount;

    /**
     * 活跃趋势
     */
    private Map<String, Object> activityTrend;
}
