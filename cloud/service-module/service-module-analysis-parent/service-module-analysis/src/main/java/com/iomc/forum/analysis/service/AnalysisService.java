package com.iomc.forum.analysis.service;


import com.iomc.forum.analysis.api.vo.SiteCountDailyStats;
import com.iomc.forum.analysis.api.vo.SiteCountVO;
import com.iomc.forum.analysis.api.vo.UserStatVO;

import java.util.List;

public interface AnalysisService {

    /**
     * 获取用户统计信息
     *
     * @param currentUserId
     * @param userId
     * @return
     */
    UserStatVO getUserStat(Long currentUserId,
                           Long userId);

    /**
     * 查询站点统计概览
     */
    SiteCountVO selectSiteCount();

    /**
     * 查询活跃统计
     * @return
     */
    List<SiteCountDailyStats> getDailyTrend();
}
