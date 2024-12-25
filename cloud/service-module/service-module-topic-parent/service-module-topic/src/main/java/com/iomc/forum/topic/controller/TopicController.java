package com.iomc.forum.topic.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.common.core.Res;
import com.iomc.common.security.utils.SecurityUtils;
import com.iomc.forum.analysis.api.feign.AnalysisServiceFeign;
import com.iomc.forum.analysis.api.vo.UserStatVO;
import com.iomc.forum.topic.api.dto.TopicDTO;
import com.iomc.forum.topic.api.vo.TopicVO;
import com.iomc.forum.topic.dao.HotDao;
import com.iomc.forum.topic.service.TopicService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/topic")
@RequiredArgsConstructor
public class TopicController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private AnalysisServiceFeign analysisServiceFeign;

    @Autowired
    private HotDao hotMapper;

    /**
     * 获取话题列表
     *
     * @param page
     * @param size
     * @param categoryId
     * @param type
     * @param sort
     *
     */
    @GetMapping
    public Res list(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String sort) {
        return topicService.getTopicList(Page.of(page, size), categoryId, type, sort);
    }

    /**
     * 获取话题详情
     *
     * @param id
     */
    @GetMapping("/{id}")
    public Res<TopicVO> detail(@PathVariable Long id) {
        return topicService.getTopicDetail(id);
    }

    /**
     * 创建话题
     *
     * @param topicDTO
     */
    @PostMapping
    public Res create(@RequestBody @Validated TopicDTO topicDTO) {
        topicService.createTopic(topicDTO);
        return Res.success();
    }

    /**
     * 更新话题
     *
     * @param id
     * @param topicDTO
     */
    @PutMapping("/{id}")
    public Res update(@PathVariable Long id, @RequestBody @Validated TopicDTO topicDTO) {
        topicDTO.setId(id);
        topicService.updateTopic(topicDTO);
        return Res.success();
    }

    /**
     * 删除话题
     *
     * @param id
     */
    @DeleteMapping("/{id}")
    public Res delete(@PathVariable Long id) {
        topicService.deleteTopic(id);
        return Res.success();
    }

    /**
     * 点赞话题
     *
     * @param id
     */
    @PostMapping("/{id}/like")
    public Res like(@PathVariable Long id) {
        topicService.likeTopic(id, true);
        return Res.success();
    }

    /**
     * 取消点赞
     *
     * @param id
     */
    @DeleteMapping("/{id}/like")
    public Res unlike(@PathVariable Long id) {
        topicService.likeTopic(id, false);
        return Res.success();
    }

    /**
     * 收藏话题
     *
     * @param id
     */
    @PostMapping("/{id}/collect")
    public Res collect(@PathVariable Long id) {
        topicService.collectTopic(id, true);
        return Res.success();
    }

    /**
     * 取消收藏
     *
     * @param id
     */
    @DeleteMapping("/{id}/collect")
    public Res unCollect(@PathVariable Long id) {
        topicService.collectTopic(id, false);
        return Res.success();
    }

    /**
     * 作者相关信息
     *
     * @param id
     * @return
     */
    @GetMapping("/author/{id}")
    public Res analysis(@PathVariable Long id) {

        Long currentUserId = null;
        if (SecurityUtils.isLogin()) {
            currentUserId = SecurityUtils.getCurrentUserId();
        }

        return analysisServiceFeign.getUserStat(currentUserId, id);
    }

    /**
     * 关注
     *
     * @param id
     */
    @PostMapping("/follow/{id}")
    public Res follow(@PathVariable Long id) {
        topicService.follow(id, true);
        return Res.success();
    }

    /**
     * 取消关注
     *
     * @param id
     */
    @DeleteMapping("/follow/{id}")
    public Res unFollow(@PathVariable Long id) {
        topicService.follow(id, false);
        return Res.success();
    }


    /**
     * 获取推荐话题
     *
     * @param limit
     */
    @GetMapping("/recommend")
    public Res<List<TopicVO>> getRecommendTopics(@RequestParam("limit") Integer limit,
                                                 @RequestParam(required = false, value = "categoryId") Long categoryId) {

        Long currentUserId = null;
        if (SecurityUtils.isLogin()) {
            currentUserId = SecurityUtils.getCurrentUserId();
        }

        return Res.success(hotMapper.getRecommendedTopics(currentUserId, categoryId, limit));
    }

    /**
     * 获取活跃用户
     *
     * @param limit
     */
    @GetMapping("/activeUser")
    public Res<List<UserStatVO>> getHotUsers(@RequestParam("limit") Integer limit) {
        return Res.success(hotMapper.getActiveUsers(limit));
    }

    /**
     * 获取作者相关话题
     *
     * @param userId
     */
    @GetMapping("/author")
    public Res<List<TopicVO>> getAuthorTopics(@RequestParam("userId") Long userId,
                                              @RequestParam(value = "keyword", required = false) String keyword,
                                              @RequestParam(value = "limit", required = false) Integer limit) {

        return topicService.getAuthorTopics(Page.of(1, limit), userId, keyword);
    }
} 