package com.iomc.forum.topic.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.iomc.forum.topic.api.entity.Topic;
import com.iomc.forum.topic.api.vo.TopicVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface TopicDao extends BaseMapper<Topic> {

    /**
     * 分页查询话题列表
     *
     * @param page 分页参数
     * @param categoryId 分类ID（可选）
     * @param type 类型（可选）0全部 1热门 2最新
     * @param sort 排序方式（可选）0最新发布 1最多浏览 2最多评论
     * @param keyword
     * @param userId
     *
     * @return 话题列表
     */
    IPage<TopicVO> selectTopicList(Page<TopicVO> page,
                                   @Param("categoryId") Long categoryId,
                                   @Param("type") String type, @Param("sort") String sort,
                                   @Param("keyword") String keyword,
                                   @Param("userId") Long userId);


    /**
     * 更新话题浏览数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET view_count = view_count + 1 WHERE id = #{id}")
    int incrViewCount(@Param("id") Long id);

    /**
     * 增加话题点赞数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET like_count = like_count + 1 WHERE id = #{id}")
    int incrLikeCount(@Param("id") Long id);

    /**
     * 减少话题点赞数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET like_count = like_count - 1 WHERE id = #{id}")
    int decrLikeCount(@Param("id") Long id);

    /**
     * 增加话题收藏数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET collect_count = collect_count + 1 WHERE id = #{id}")
    int incrCollectCount(@Param("id") Long id);

    /**
     * 减少话题收藏数
     * @param id
     * @return
     */
    @Update("UPDATE topics SET collect_count = collect_count - 1 WHERE id = #{id}")
    int decrCollectCount(@Param("id") Long id);

    /**
     * 增加话题评论数
     * @param id
     */
    @Update("UPDATE topics SET comment_count = comment_count + 1 WHERE id = #{id}")
    int incrCommentCount(@Param("id") Long id);

    /**
     * 减少话题评论数
     * @param id
     */
    @Update("UPDATE topics SET comment_count = comment_count - 1 WHERE id = #{id}")
    int decrCommentCount(@Param("id") Long id);


    /**
     * 分页查询用户话题列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     * @return 话题列表
     */
    IPage<TopicVO> selectTopicListByUserId(@Param("page") Page<TopicVO> page,
                                           @Param("userId") Long userId,
                                           @Param("keyword") String keyword);

    /**
     * 分页查询用户收藏话题列表
     *
     * @param page 分页参数
     * @param userId 用户ID
     * @param keyword 关键词
     *
     * @return 话题列表
     */
    IPage<TopicVO> selectCollectListByUserId(@Param("page") Page<TopicVO> page,
                                             @Param("userId") Long userId,
                                             @Param("keyword") String keyword);
} 