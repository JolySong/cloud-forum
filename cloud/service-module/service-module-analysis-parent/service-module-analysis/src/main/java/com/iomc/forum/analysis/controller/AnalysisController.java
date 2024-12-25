package com.iomc.forum.analysis.controller;

import com.iomc.common.core.Res;
import com.iomc.forum.analysis.api.vo.SiteCountDailyStats;
import com.iomc.forum.analysis.api.vo.SiteCountVO;
import com.iomc.forum.analysis.service.AnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/analysis")
public class AnalysisController {

    @Autowired
    private AnalysisService analysisService;


    /**
     * 获取站点统计数据
     */
    @GetMapping("/siteCount")
    public Res<SiteCountVO> getDailyActivityStats() {
        // 获取总计数据
        SiteCountVO siteCountVO = analysisService.selectSiteCount();

        // 获取趋势数据
        List<SiteCountDailyStats> dailyTrend = analysisService.getDailyTrend();

        // 组装趋势数据
        Map<String, Object> activityTrend = new HashMap<>();
        activityTrend.put("dates", dailyTrend.stream()
                .map(SiteCountDailyStats::getDate)
                .collect(Collectors.toList()));
        activityTrend.put("topics", dailyTrend.stream()
                .map(SiteCountDailyStats::getTopicCount)
                .collect(Collectors.toList()));
        activityTrend.put("comments", dailyTrend.stream()
                .map(SiteCountDailyStats::getCommentCount)
                .collect(Collectors.toList()));

        // 组装最终结果
        siteCountVO.setActivityTrend(activityTrend);

        return Res.success(siteCountVO);
    }

}
