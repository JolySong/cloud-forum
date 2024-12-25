package com.iomc.forum.topic.dao;


import com.iomc.forum.analysis.api.vo.UserStatVO;
import com.iomc.forum.topic.api.vo.CategoryVO;
import com.iomc.forum.topic.api.vo.CommentVO;
import com.iomc.forum.topic.api.vo.TopicVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface HotDao {

    /**
     * 获取热门话题
     */
    List<TopicVO> getHotTopics(Integer limit);

    /**
     * 获取热门分类
     * <p>
     * 使用加权计算来综合评估热门程度：
     * 话题数量权重 30%
     * 浏览量权重 20%
     * 评论数权重 20%
     * 点赞数权重 10%
     * 精选话题数权重 10%
     * 最近话题数权重 10%
     */
    List<CategoryVO> getHotCategories(Integer limit);

    /**
     * 获取热门评论
     * <p>
     * 评论本身的点赞数（30%权重）
     * 回复数量（30%权重）
     * 回复获得的总点赞数（20%权重）
     * 时间衰减（20%权重）
     * <p>
     * 时间衰减
     * 24小时内：50分
     * 7天内：30分
     * 30天内：10分
     */
    List<CommentVO> getHotComments(Integer limit);


    /**
     * 获取活跃用户
     *
     *         topic_count * 10 +  -- 发帖数权重
     *         total_views * 0.01 +  -- 总浏览量权重
     *         total_topic_likes * 2 +  -- 话题获赞权重
     *         total_topic_comments * 2 +  -- 话题评论数权重
     *         recent_topics * 20 +  -- 最近发帖权重
     *         comment_count * 5 +  -- 评论数权重
     *         recent_comments * 10 +  -- 最近评论权重
     *         comment_likes_received  -- 评论获赞权重
     */
    List<UserStatVO> getActiveUsers(Integer limit);


    /**
     * 获取推荐话题
     *
     * @param userId
     * @param categoryId
     * @param limit
     * @return
     */
    List<TopicVO> getRecommendedTopics(@Param("userId") Long userId,
                                       @Param("categoryId") Long categoryId,
                                       @Param("limit") int limit);
}
