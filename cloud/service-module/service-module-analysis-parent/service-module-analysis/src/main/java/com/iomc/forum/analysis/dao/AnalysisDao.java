package com.iomc.forum.analysis.dao;


import com.iomc.forum.analysis.api.vo.SiteCountDailyStats;
import com.iomc.forum.analysis.api.vo.SiteCountVO;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AnalysisDao {

    /**
     * 获取用户统计信息
     */
    UserStatVO selectUserStats(@Param("userId") Long userId, @Param("currentUserId") Long currentUserId);

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
