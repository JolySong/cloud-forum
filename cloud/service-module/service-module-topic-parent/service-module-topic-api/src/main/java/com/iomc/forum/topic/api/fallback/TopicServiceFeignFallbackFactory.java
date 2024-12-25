package com.iomc.forum.topic.api.fallback;


import com.iomc.common.core.Res;
import com.iomc.forum.topic.api.feign.TopicServiceFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;


/**
 * 话题服务调用失败的降级处理
 */
@Component
public class TopicServiceFeignFallbackFactory implements FallbackFactory<TopicServiceFeign> {

    private static final Logger log =
            LoggerFactory.getLogger(TopicServiceFeignFallbackFactory.class);

    @Override
    public TopicServiceFeign create(Throwable throwable) {

        return new TopicServiceFeign() {


            /**
             * 获取用户的主题列表
             *
             * @param page
             * @param size
             * @param userId
             * @param keyword
             * @return
             */
            @Override
            public Res getMyTopics(Integer page, Integer size, Long userId, String keyword) {
                log.error("获取用户的主题列表失败，参数：page={},size={},userId={},keyword={}", page, size, userId, keyword);
                return null;
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
            @Override
            public Res getMyCollects(Integer page, Integer size, Long userId, String keyword) {
                log.error("获取用户的收藏列表失败，参数：page={},size={},userId={},keyword={}", page, size, userId, keyword);
                return null;
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
            @Override
            public Res getMyComments(Integer page, Integer size, Long userId, String keyword) {
                log.error("获取用户的评论列表失败，参数：page={},size={},userId={},keyword={}", page, size, userId, keyword);
                return null;
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
            @Override
            public Res getMyLikes(Integer page, Integer size, Long userId, String keyword) {
                log.error("获取用户的点赞列表失败，参数：page={},size={},userId={},keyword={}", page, size, userId, keyword);
                return null;
            }
        };
    }
}
