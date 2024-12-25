package com.iomc.forum.topic.controller;


import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.vo.CategoryVO;
import com.iomc.forum.topic.api.vo.CommentVO;
import com.iomc.forum.topic.api.vo.TopicVO;
import com.iomc.forum.topic.dao.HotDao;
import com.iomc.forum.user.api.feign.UserServiceFeign;
import com.iomc.forum.user.api.vo.UserProfileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class HotController {

    @Autowired
    private HotDao hotMapper;

    @Autowired
    private UserServiceFeign userServiceFeign;

    /**
     * 获取热门话题
     *
     * @param limit
     */
    @GetMapping("/topic/hot/topics")
    public Res<List<TopicVO>> getHotTopics(@RequestParam("limit") Integer limit) {
        List<TopicVO> hotTopics = hotMapper.getHotTopics(limit);
        if (hotTopics != null) {
            for (TopicVO hotTopic : hotTopics) {
                Res<UserProfileVO> res = userServiceFeign.getUserProfile(hotTopic.getUserId());
                UserProfileVO userProfile = res.getData();
                if (null == userProfile || null == userProfile.getId()) {
                    log.info("用户服务->getUserProfile()->调用未查询到信息:{} 参数: {}", res.getMessage(), hotTopic.getUserId());
                }
                hotTopic.setAuthor(userProfile);
            }
        }

        return Res.success(hotTopics);
    }

    /**
     * 获取热门评论
     *
     * @param limit
     */
    @GetMapping("/topic/hot/comments")
    public Res<List<CommentVO>> getHotComments(@RequestParam("limit") Integer limit) {
        return Res.success(hotMapper.getHotComments(limit));
    }

    /**
     * 获取热门分类
     *
     * @param limit
     */
    @GetMapping("/topic/hot/categories")
    public Res<List<CategoryVO>> getHotCategories(@RequestParam("limit") Integer limit) {
        return Res.success(hotMapper.getHotCategories(limit));
    }
}
