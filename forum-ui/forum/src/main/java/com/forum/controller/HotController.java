package com.forum.controller;


import com.forum.common.R;
import com.forum.common.utils.SecurityUtils;
import com.forum.mapper.HotMapper;
import com.forum.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
public class HotController {

    @Autowired
    private HotMapper hotMapper;

    /**
     * 获取热门话题
     *
     * @param limit
     */
    @GetMapping("/hot/topics")
    public R<List<TopicVO>> getHotTopics(@RequestParam("limit") Integer limit) {
        return R.ok(hotMapper.getHotTopics(limit));
    }

    /**
     * 获取热门评论
     *
     * @param limit
     */
    @GetMapping("/hot/comments")
    public R<List<CommentVO>> getHotComments(@RequestParam("limit") Integer limit) {
        return R.ok(hotMapper.getHotComments(limit));
    }

    /**
     * 获取热门分类
     *
     * @param limit
     */
    @GetMapping("/hot/categories")
    public R<List<CategoryVO>> getHotCategories(@RequestParam("limit") Integer limit) {
        return R.ok(hotMapper.getHotCategories(limit));
    }

    /**
     * 获取推荐话题
     *
     * @param limit
     */
    @GetMapping("/hot/recommend")
    public R<List<TopicVO>> getRecommendTopics(@RequestParam("limit") Integer limit,
                                               @RequestParam(required = false, value = "categoryId") Long categoryId) {
        return R.ok(hotMapper.getRecommendedTopics(SecurityUtils.getCurrentUserId(), categoryId, limit));
    }

    /**
     * 获取热门用户
     *
     * @param limit
     */
    @GetMapping("/hot/users")
    public R<List<UserVO>> getHotUsers(@RequestParam("limit") Integer limit) {
        return R.ok(hotMapper.getActiveUsers(limit));
    }

    /**
     * 获取站点统计数据
     */
    @GetMapping("/hot/count")
    public R<SiteCountVO> getDailyActivityStats() {
        // 获取总计数据
        SiteCountVO siteCountVO = hotMapper.selectSiteCount();

        // 获取趋势数据
        List<SiteCountDailyStats> dailyTrend = hotMapper.getDailyTrend();

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

        return R.ok(siteCountVO);
    }

}
