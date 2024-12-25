package com.forum.vo;

import lombok.Data;

import java.util.Map;

@Data
public class SiteCountVO {
    private Integer userCount;
    private Integer topicCount;
    private Integer commentCount;
    private Integer viewCount;
    private Map<String, Object> activityTrend;
}
