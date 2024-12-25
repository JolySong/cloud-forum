package com.iomc.forum.topic.api.feign;


import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.fallback.TopicServiceFeignFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 用户服务接口
 */
@FeignClient(contextId = "topicServiceFeign", value = "service-module-topic",
        fallbackFactory = TopicServiceFeignFallbackFactory.class)
public interface TopicServiceFeign {


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
    Res getMyTopics(@RequestParam("page") Integer page,
                    @RequestParam("size") Integer size,
                    @RequestParam("userId") Long userId,
                    @RequestParam("keyword") String keyword);


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
    Res getMyCollects(@RequestParam("page") Integer page,
                      @RequestParam("size") Integer size,
                      @RequestParam("userId") Long userId,
                      @RequestParam("keyword") String keyword);

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
    Res getMyComments(@RequestParam(value = "page") Integer page,
                      @RequestParam(value = "size") Integer size,
                      @RequestParam(value = "userId") Long userId,
                      @RequestParam(value = "keyword") String keyword);

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
    Res getMyLikes(@RequestParam(value = "page") Integer page,
                   @RequestParam(value = "size") Integer size,
                   @RequestParam(value = "userId") Long userId,
                   @RequestParam(value = "keyword") String keyword);
}
