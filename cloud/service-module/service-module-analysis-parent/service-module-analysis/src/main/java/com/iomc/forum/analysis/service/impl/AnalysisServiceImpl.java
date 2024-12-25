package com.iomc.forum.analysis.service.impl;


import com.iomc.forum.analysis.api.vo.SiteCountDailyStats;
import com.iomc.forum.analysis.api.vo.SiteCountVO;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import com.iomc.forum.analysis.dao.AnalysisDao;
import com.iomc.forum.analysis.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnalysisServiceImpl implements AnalysisService {

    @Autowired
    private AnalysisDao analysisDao;


    /**
     * 获取用户统计信息
     *
     * @param currentUserId
     * @param userId
     * @return
     */
    @Override
    public UserStatVO getUserStat(Long currentUserId, Long userId) {
        return analysisDao.selectUserStats(userId, currentUserId);
    }

    /**
     * 查询站点统计概览
     */
    @Override
    public SiteCountVO selectSiteCount() {
        return analysisDao.selectSiteCount();
    }

    /**
     * 查询活跃统计
     *
     * @return
     */
    @Override
    public List<SiteCountDailyStats> getDailyTrend() {
        return analysisDao.getDailyTrend();
    }
}
