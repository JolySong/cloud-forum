package com.iomc.forum.topic.controller;


import com.iomc.common.core.Res;
import com.iomc.forum.topic.service.CommentService;
import com.iomc.forum.topic.service.LikeService;
import com.iomc.forum.topic.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InnerController {

    @Autowired
    private TopicService topicService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    /**
     * 获取用户的主题列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @GetMapping("/inner/user/topics")
    public Res getMyTopics(@RequestParam("page") Integer page,
                           @RequestParam("size") Integer size,
                           @RequestParam("userId") Long userId,
                           @RequestParam(required = false, value = "keyword") String keyword) {
        return topicService.getMyTopics(page, size, userId, keyword);
    }


    /**
     * 获取用户的收藏列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @GetMapping("/inner/user/collects")
    public Res getMyCollects(@RequestParam("page") Integer page,
                             @RequestParam("size") Integer size,
                             @RequestParam("userId") Long userId,
                             @RequestParam(required = false, value = "keyword") String keyword) {
        return topicService.getMyCollects(page, size, userId, keyword);
    }

    /**
     * 获取用户的评论列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @GetMapping("/inner/user/comments")
    public Res getMyComments(@RequestParam("page") Integer page,
                             @RequestParam("size") Integer size,
                             @RequestParam("userId") Long userId,
                             @RequestParam(required = false, value = "keyword") String keyword) {
        return commentService.getMyComments(page, size, userId, keyword);
    }

    /**
     * 获取用户的点赞列表
     *
     * @param page
     * @param size
     * @param userId
     * @param keyword
     * @return
     */
    @GetMapping("/inner/user/likes")
    public Res getMyLikes(@RequestParam("page") Integer page,
                          @RequestParam("size") Integer size,
                          @RequestParam("userId") Long userId,
                          @RequestParam(required = false, value = "keyword") String keyword) {
        return likeService.getMyLikes(page, size, userId, keyword);
    }
}
