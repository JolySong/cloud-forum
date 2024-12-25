package com.forum.vo;

import lombok.Data;

@Data
public class SiteCountDailyStats {
    private String date;
    private Integer topicCount;
    private Integer commentCount;
}
